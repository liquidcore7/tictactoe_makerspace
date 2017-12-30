package visualization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

public abstract class ObjDrawer<T> {
    protected BufferedImage image;
    private int height;
    private int width;

    public ObjDrawer() {}

    public ObjDrawer(int squareSz) {
        height = width = squareSz;
    }

    public ObjDrawer(int x, int y) {
        height = y;
        width = x;
    }

    public abstract ObjDrawer<T> draw(T o);

    public void save(String filename) {
        try {
            File out = new File(filename);
            String extension = filename.substring(filename.indexOf('.') + 1);
            ImageIO.write(image, extension, out);
        } catch (Exception e) {
            System.err.println("Failed to save image: " + filename);
        }
    }

    public final Optional<BufferedImage> getImage() {
        return Optional.ofNullable(image);
    }

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }
}
