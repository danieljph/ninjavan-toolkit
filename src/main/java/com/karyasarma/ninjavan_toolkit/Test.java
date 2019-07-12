package com.karyasarma.ninjavan_toolkit;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Daniel Joi Partogi Hutapea
 */
public class Test
{
    public static void main(String[] args) throws IOException
    {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        try
        {
            while(true)
            {
                try
                {
                    BufferedImage bufferedImage = webcam.getImage();
                    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
                    Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
                    System.out.println(qrCodeResult.getText());
                }
                catch(NotFoundException ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
        }
        finally
        {
            webcam.close();
        }
    }

    public static void pause(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch(InterruptedException ignore)
        {
        }
    }
}
