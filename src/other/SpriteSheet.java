package other;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage sheet;
    private int width, height;

    public SpriteSheet(String s, int size) {
        this(s, size, size);
    }

    public SpriteSheet(String name, int width, int height) {
        sheet = ImageLoader.load(name);
        this.width = width;
        this.height = height;
    }

    public BufferedImage grabImage(int row, int col) {
        return sheet.getSubimage((col - 1) * width, (row - 1) * height, width, height);
    }
}
