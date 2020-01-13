package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

class CreateImageThread extends Thread {

    public void run(BufferedImage originalImage, int mask, ArrayList<BufferedImage> resultList, int index) {
        createColorImageThreadTask(originalImage, mask, resultList, index);
    }

    private synchronized void createColorImageThreadTask(BufferedImage imageChunk   , int mask, ArrayList<BufferedImage> resultList, int index) {
        BufferedImage colorImage = new BufferedImage(imageChunk.getWidth(),
                imageChunk.getHeight(), imageChunk.getType());

        for (int x = 0; x < imageChunk.getWidth(); x++) {
            for (int y = 0; y < imageChunk.getHeight(); y++) {
                int pixel = imageChunk.getRGB(x, y) & mask;
                colorImage.setRGB(x, y, pixel);
            }
        }
        resultList.add(index, colorImage);
    }
}
