package visualization;

import com.makerspace.CellState;
import com.makerspace.FieldState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FieldImage extends ObjDrawer<FieldState> {

    private Image X;
    private Image O;

    public FieldImage(int pieceSize) {
        super(pieceSize*3);
        X = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_BYTE_GRAY);
        O = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_BYTE_GRAY);

        X.getGraphics().drawLine(0,0, pieceSize, pieceSize);
        X.getGraphics().drawLine(0, pieceSize, pieceSize, 0);
        O.getGraphics().drawOval(0,0, pieceSize, pieceSize);
    }

    private void drawBounds() {
        Graphics drawer = image.getGraphics();
        for (int i = 1; i < 3; i++) {
            drawer.drawLine(0, getHeight()/3*i, getWidth(), getHeight()/3*i);
            drawer.drawLine(getWidth()/3*i, 0, getWidth()/3*i, getHeight());
        }
    }

    public FieldImage draw(FieldState fieldState) {
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)

                if (fieldState.hasSet(i*3 + j))
                    image.getGraphics().drawImage(
                        fieldState.getCell(i*3 + j) == CellState.X ? X : O,
                        getWidth() / 3 * i, getHeight() / 3 * j, null
                    );
        drawBounds();
        return this;
    }
}
