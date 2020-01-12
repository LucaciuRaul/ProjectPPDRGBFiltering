package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RGBDisplayModel {

    private BufferedImage originalImage;
    private BufferedImage redImage;
    private BufferedImage greenImage;
    private BufferedImage blueImage;

    public void setOriginalImage(BufferedImage originalImage) throws InterruptedException {
        this.originalImage = originalImage;
        this.makeRGBImages();
    }

    private void makeRGBImages() throws InterruptedException {
        this.redImage = createColorImage(originalImage, 0xFFFF0000);
        this.greenImage = createColorImage(originalImage, 0xFF00FF00);
        this.blueImage = createColorImage(originalImage, 0xFF0000FF);
    }

    public BufferedImage getRedImage() {
        return redImage;
    }

    public BufferedImage getGreenImage() {
        return greenImage;
    }

    public BufferedImage getBlueImage() {
        return blueImage;
    }

    private ArrayList<BufferedImage> splitImage(BufferedImage originalImage, int size) {
        ArrayList<BufferedImage> imgArray = new ArrayList<>();
        for (int x = 0; x < originalImage.getWidth(); x += size)
            for (int y = 0; y < originalImage.getHeight(); y += size) {
                imgArray.add(originalImage.getSubimage(x, y, size, size));
            }
        return imgArray;
    }

    private BufferedImage createColorImage(BufferedImage originalImage, int mask) throws InterruptedException {
        ArrayList<BufferedImage> resultList = new ArrayList<>();
        List<CreateImageThread> threads = new ArrayList<>();

        int imgChunkSize = 100;
        ArrayList<BufferedImage> images = this.splitImage(originalImage, imgChunkSize);

        for (int i = 0; i < images.size(); i++) {
            CreateImageThread thread = new CreateImageThread();
            thread.run(images.get(i), mask, resultList, i);
            threads.add(thread);
        }
        for (CreateImageThread thread : threads) {
            thread.join();
        }

        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
        int howManyOnLine = (int) Math.sqrt(resultList.size());
        for (int i = 0; i < howManyOnLine; i++) {
            for (int j = 0; j < howManyOnLine; j++)
                resultImage.createGraphics().drawImage(resultList.get(i * howManyOnLine + j), imgChunkSize * i, imgChunkSize * j, null);
        }
        return resultImage;
    }

    public BufferedImage getImage() throws URISyntaxException, IOException {
        return ImageIO.read(new File(getClass().getResource("img.jpg").toURI()));
    }
}
