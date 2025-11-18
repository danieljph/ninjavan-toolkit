package com.karyasarma.toolkit.doku.util;

import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class Mp3Utils
{
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private Mp3Utils()
    {
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
        EXECUTOR_SERVICE.submit(() ->
        {
            try
            {
                InputStream in = Mp3Utils.class.getResourceAsStream("/sound/" + soundName);

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
}
