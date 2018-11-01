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
}
