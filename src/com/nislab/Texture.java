package com.nislab;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
    public int[] pixels;
    private String location;
    public final int SIZE;

    public Texture(String location, int size) {
        this.location = location;
        this.SIZE = size;
        this.pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(location));
            int width = image.getWidth();
            int height = image.getHeight();
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Texture wood = new Texture("/home/bangie/Documents/java/3d-gameengine-demo/src/com/nislab/assets/wood.png", 64);
    public static Texture brick = new Texture("/home/bangie/Documents/java/3d-gameengine-demo/src/com/nislab/assets/redbrick.png", 64);
    public static Texture blueStone = new Texture("/home/bangie/Documents/3d-gameengine-demo/java/src/com/nislab/assets/bluestone.png", 64);
    public static Texture stone = new Texture("/home/bangie/Documents/java/3d-gameengine-demo/src/com/nislab/assets/greystone.png", 64);
}
