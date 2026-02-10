package com.karyasarma.toolkit.doku.util;

import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class Mp3Utils
{
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private static boolean soundEnabled = Boolean.parseBoolean(System.getProperty("app.sound.enabled", "true"));

    private Mp3Utils()
    {
    }

    public synchronized static String getEnableSoundMiLabel()
    {
        return Mp3Utils.soundEnabled? "Disable Sound" : "Enable Sound";
    }

    public synchronized static void setSoundEnabled(boolean soundEnabled)
    {
        Mp3Utils.soundEnabled = soundEnabled;
    }

    public synchronized static boolean isSoundDisabled()
    {
        return !Mp3Utils.soundEnabled;
    }

    public static void playProcessSuccess()
    {
        play("process-success.mp3");
    }

    public static void playProcessFailed()
    {
        play("process-failed.mp3");
    }

    @SuppressWarnings("SameParameterValue")
    private static void play(String soundName)
    {
        if(isSoundDisabled())
        {
            return;
        }

        EXECUTOR_SERVICE.submit(() ->
        {
            try
            {
                InputStream in = getAudioInputStream(soundName);

                if(in != null)
                {
                    AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();

                    Player player = new Player(in, audioDevice);
                    player.play();
                    player.close();
                }
            }
            catch(Exception ex)
            {
                System.out.println("Problem playing audio.");
                ex.printStackTrace(System.err);
            }
        });
    }

    private static InputStream getAudioInputStream(String soundName)
    {
        InputStream result = null;

        try
        {
            File jarFile = new File(Mp3Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            System.out.println("Jar File: " + jarFile.getAbsolutePath());

            File file = new File(jarFile.getParent(), "/sound/" + soundName);
            System.out.format("File '%s' exists: %s%n", file.getAbsolutePath(), file.exists());

            if(file.exists())
            {
                result = Files.newInputStream(file.toPath());
            }
        }
        catch(Exception ex)
        {
            System.out.println("Problem getting audio input stream from file. We'll use the default one.");
            ex.printStackTrace(System.err);
        }

        if(result == null)
        {
            result = Mp3Utils.class.getResourceAsStream("/sound/" + soundName);
        }

        return result;
    }
}
