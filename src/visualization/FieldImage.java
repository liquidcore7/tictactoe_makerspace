package visualization;

import com.makerspace.CellState;
import com.makerspace.FieldState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FieldImage implements ObjDrawer<FieldState> {

    private Image X;
    private Image O;
    private int size;

    public FieldImage(int pieceSize) {
        size = pieceSize;
        X = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
        O = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);

        X.getGraphics().drawLine(0,0, size, size);
        X.getGraphics().drawLine(0, size, size, 0);
        O.getGraphics().drawOval(0,0, size, size);
    }

    private BufferedImage drawBounds(BufferedImage raw) {
        Graphics drawer = raw.getGraphics();
        for (int i = 1; i < 3; i++) {
            drawer.drawLine(0, size*i, size*3, size*i);
            drawer.drawLine(size*i, 0, size*i, size*3);
        }
        return raw;
    }

    public BufferedImage draw(FieldState fieldState) {
        BufferedImage field = new BufferedImage(size * 3, size * 3, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field.getGraphics().drawImage(
                        fieldState.getCell(i*3 + j) == CellState.X ? X : O,
                        size * i, size * j, null
                );
            }
        }
        return drawBounds(field);
    }

    public void saveImage(RenderedImage image, String filename) {
        try {
            File out = new File(filename);
            String extension = filename.substring(filename.indexOf('.') + 1);
            ImageIO.write(image, extension, out);
        } catch (IOException e) {
            System.err.println("Failed to save image: " + filename);
        }
    }
}
