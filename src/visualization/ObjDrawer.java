package visualization;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public interface ObjDrawer<T> {
    public BufferedImage draw(T o);
    public void saveImage(RenderedImage image, String filename);
}
