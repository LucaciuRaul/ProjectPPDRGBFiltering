package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            RGBDisplayModel model = new RGBDisplayModel();
            BufferedImage img = model.getImage();
            model.setOriginalImage(img);

            BufferedImage redResult = model.getRedImage();
            BufferedImage greenResult = model.getGreenImage();
            BufferedImage blueResult = model.getBlueImage();


            File outputfileRED = new File("redResult.jpg");
            File outputfileGREEN = new File("greenResult.jpg");
            File outputfileBLUE = new File("blueResult.jpg");

            ImageIO.write(redResult, "jpg", outputfileRED);
            ImageIO.write(greenResult, "jpg", outputfileGREEN);
            ImageIO.write(blueResult, "jpg", outputfileBLUE);

        } catch (IOException | URISyntaxException | InterruptedException e) {
            System.out.println(e.toString());
        }
    }


}
