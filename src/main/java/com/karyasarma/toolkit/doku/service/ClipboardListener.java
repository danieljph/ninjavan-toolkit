package com.karyasarma.toolkit.doku.service;

import com.karyasarma.toolkit.doku.model.ClipboardData;
import com.karyasarma.toolkit.doku.model.ClipboardHistoryData;
import com.karyasarma.toolkit.doku.util.ClipboardUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardListener implements Runnable
{
    private final ClipboardHistoryData clipboardHistoryData = new ClipboardHistoryData(20);
    private final Menu clipboardHistoryMenu;
    private final MenuItem clearClipboardHistoryMi;

    private final AtomicBoolean isListening = new AtomicBoolean(false);

    private Thread runningThread;

    public ClipboardListener(Menu clipboardHistoryMenu, MenuItem clearClipboardHistoryMi)
    {
        this.clipboardHistoryMenu = clipboardHistoryMenu;
        this.clearClipboardHistoryMi = clearClipboardHistoryMi;
    }

    public void startListening()
    {
        isListening.set(true);

        if(runningThread != null && runningThread.isAlive())
        {
            runningThread.interrupt();
        }

        runningThread = new Thread(this);
        runningThread.start();
    }

    public void stopListening()
    {
        isListening.set(false);

        if(runningThread != null && runningThread.isAlive())
        {
            runningThread.interrupt();
        }
    }

    public void onMenuClicked()
    {
        if(isListening.get())
        {
            stopListening();
            clipboardHistoryMenu.setLabel("Clipboard History Off");
            updateMenu(true);
        }
        else
        {
            clipboardHistoryMenu.setLabel("Clipboard History On");
            updateMenu(false);
            startListening();
        }
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run()
    {
        System.out.println("ClipboardListener start listening...");

        synchronized(this)
        {
            boolean isRunning = true;

            while(isRunning && isListening.get())
            {
                try
                {
                    Transferable transferable = ClipboardUtils.getContents(null);
                    String content = (String) transferable.getTransferData(DataFlavor.stringFlavor);

                    if(StringUtils.isBlank(content) || content.length() > 100_000)
                    {
                        continue;
                    }

                    String contentHtml = transferable.isDataFlavorSupported(DataFlavor.allHtmlFlavor)
                        ? (String) transferable.getTransferData(DataFlavor.allHtmlFlavor)
                        : null;

                    ClipboardData currentClipboardData = new ClipboardData(content, contentHtml);

                    if(isListening.get() && clipboardHistoryData.isClipboardDataChanged(currentClipboardData))
                    {
                        clipboardHistoryData.push(currentClipboardData);
                        updateMenu(false);
                    }

                    Thread.sleep(500);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace(System.err);
                    isRunning = false;
                }
                catch(Exception ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
        }

        System.out.println("ClipboardListener stop listening.");
    }

    public void clearClipboardHistory()
    {
        ClipboardUtils.clearClipboard();
        clipboardHistoryData.clear();
        updateMenu(true);
    }

    private void updateMenu(boolean clearMenu)
    {
        SwingUtilities.invokeLater(() ->
        {
            clipboardHistoryMenu.removeAll();

            if(!clearMenu)
            {
                int index = 0;
                int keyEvent = KeyEvent.VK_1;

                for(ClipboardData clipboardData : clipboardHistoryData)
                {
                    MenuShortcut menuShortcut = null;

                    if(index < 9)
                    {
                        menuShortcut = new MenuShortcut(keyEvent++);
                    }

                    String menuItemLabelPrefix = clipboardData.isHtmlFlavor() ? "⛳ " : "";

                    MenuItem menuItem = new MenuItem(menuItemLabelPrefix + clipboardData.getContent());
                    menuItem.addActionListener(it -> copyToClipboard(clipboardData));
                    menuItem.setShortcut(menuShortcut);
                    clipboardHistoryMenu.add(menuItem);

                    index++;
                }

                if(!clipboardHistoryData.isEmpty())
                {
                    clipboardHistoryMenu.addSeparator();
                    clipboardHistoryMenu.add(clearClipboardHistoryMi);
                }
            }
        });
    }

    private void copyToClipboard(ClipboardData clipboardData)
    {
        if(clipboardData.isHtmlFlavor())
        {
            ClipboardUtils.copyToClipboard(clipboardData);
        }
        else
        {
            ClipboardUtils.copyToClipboard(clipboardData.getContent());
        }
    }
}
