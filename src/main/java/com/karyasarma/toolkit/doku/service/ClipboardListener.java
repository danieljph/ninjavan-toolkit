package com.karyasarma.toolkit.doku.service;

import com.karyasarma.toolkit.doku.model.ClipboardData;
import com.karyasarma.toolkit.doku.model.ClipboardHistoryData;
import com.karyasarma.toolkit.doku.util.ClipboardUtils;
import com.karyasarma.toolkit.doku.util.ConfluenceUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class ClipboardListener implements Runnable
{
    private static final int MAX_CLIPBOARD_DATA_LENGTH = 100_000;

    private final ClipboardHistoryData clipboardHistoryData = new ClipboardHistoryData(20);
    private final Menu clipboardHistoryMenu;
    private final MenuItem clearClipboardHistoryMi;
    private final CheckboxMenuItem keepPositionMi;

    private final AtomicBoolean isListening = new AtomicBoolean(false);

    private Thread runningThread;

    public ClipboardListener(Menu clipboardHistoryMenu, MenuItem clearClipboardHistoryMi, CheckboxMenuItem keepPositionMi)
    {
        this.clipboardHistoryMenu = clipboardHistoryMenu;
        this.clearClipboardHistoryMi = clearClipboardHistoryMi;
        this.keepPositionMi = keepPositionMi;
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
                    boolean isHtmlFlavor = transferable.isDataFlavorSupported(DataFlavor.allHtmlFlavor);
                    String content;
                    String contentHtml;

                    if(isHtmlFlavor)
                    {
                        contentHtml = (String) transferable.getTransferData(DataFlavor.allHtmlFlavor);

                        if(StringUtils.isBlank(contentHtml) || contentHtml.length() > MAX_CLIPBOARD_DATA_LENGTH)
                        {
                            continue;
                        }

                        content = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    }
                    else
                    {
                        content = (String) transferable.getTransferData(DataFlavor.stringFlavor);

                        if(StringUtils.isBlank(content) || content.length() > MAX_CLIPBOARD_DATA_LENGTH)
                        {
                            continue;
                        }

                        contentHtml = null;
                    }

                    ClipboardData currentClipboardData = new ClipboardData(content, contentHtml);

                    if(isListening.get() && clipboardHistoryData.isClipboardDataChanged(currentClipboardData))
                    {
                        clipboardHistoryData.push(currentClipboardData, keepPositionMi.getState());
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
        clipboardHistoryData.setActiveClipboardData(null);
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

                for(ClipboardData clipboardData : clipboardHistoryData)
                {
                    MenuItem menuItem = createMenuItem(clipboardData, index++);
                    clipboardHistoryMenu.add(menuItem);
                }

                if(!clipboardHistoryData.isEmpty())
                {
                    clipboardHistoryMenu.addSeparator();
                    clipboardHistoryMenu.add(keepPositionMi);
                    clipboardHistoryMenu.add(clearClipboardHistoryMi);
                }
            }
        });
    }

    private MenuItem createMenuItem(ClipboardData clipboardData, int index)
    {
        MenuShortcut menuShortcut = null;

        if(index < 9)
        {
            menuShortcut = new MenuShortcut(KeyEvent.VK_1 + index);
        }

        String menuItemLabelActive = clipboardData.equals(clipboardHistoryData.getActiveClipboardData())? "\uD83D\uDC49 " : "";
        String menuItemLabelPrefix = clipboardData.isHtmlFlavor() ? "⛳ " : "";
        String menuItemLabelContent = Optional.ofNullable(clipboardData.getContent())
            .filter(StringUtils::isNotBlank)
            .orElse(
                Optional.ofNullable(clipboardData.getContentHtml())
                    .map(ConfluenceUtils::getContentName)
                    .orElse(clipboardData.getContentHtml())
            );

        MenuItem menuItem = new MenuItem(menuItemLabelActive + menuItemLabelPrefix + menuItemLabelContent);
        menuItem.addActionListener(it ->
        {
            if((it.getModifiers() & ActionEvent.SHIFT_MASK) != 0)
            {
                if(clipboardData.equals(clipboardHistoryData.getActiveClipboardData()))
                {
                    ClipboardUtils.clearClipboard();
                    clipboardHistoryData.setActiveClipboardData(null);
                }

                clipboardHistoryData.remove(clipboardData);

                updateMenu(false);
            }
            else
            {
                copyToClipboard(clipboardData);
            }
        });
        menuItem.setShortcut(menuShortcut);

        return menuItem;
    }

    public void copyToClipboard(int clipboardIndex)
    {
        if(clipboardIndex >= 0 && clipboardIndex < clipboardHistoryData.size())
        {
            copyToClipboard(clipboardHistoryData.get(clipboardIndex));
        }
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
