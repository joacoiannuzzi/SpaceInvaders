package other;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {

    public static BufferedImage load(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ImageLoader.class.getResourceAsStream("/images/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage loadSubImage(String name, int width, int height) {
        BufferedImage image = load(name);
        image = image.getSubimage(0, 0, width, height);
        return image;
    }
}
