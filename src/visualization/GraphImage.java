package visualization;

import com.makerspace.Graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class GraphImage<NodeT, NodeDrawer extends ObjDrawer<NodeT>> implements ObjDrawer<Graph<NodeT>> {

    private BufferedImage background;
    private NodeDrawer drawer;
    private int shiftSize;

    public GraphImage(int x, int y, int cellSize, NodeDrawer drawer) {
        shiftSize = cellSize + cellSize/3;
        background = new BufferedImage(x * shiftSize,
                                        y * shiftSize,
                                            BufferedImage.TYPE_BYTE_GRAY);
        this.drawer = drawer;
    }

    public BufferedImage draw(Graph<NodeT> g) {
        Graphics2D graphics = background.createGraphics();
        int xPos = 0, currentLvl = 0;

        // TODO: rewrite graph using Node{value, level} instead of raw values
        Collection<NodeT> lvl = g.getLevel(currentLvl);
        Collection<NodeT> accu = new LinkedHashSet<>();

        while (!lvl.isEmpty()) {
            for (NodeT node : lvl) {
                graphics.drawImage(drawer.draw(node), xPos*shiftSize, currentLvl*shiftSize, null);
                Collection<NodeT> children = g.getChildren(node);
                int childPos = xPos;
                for (NodeT child : children) {
                    graphics.drawLine(xPos*shiftSize, (int) ((currentLvl + 1)*shiftSize*0.75),
                            (childPos++)*shiftSize, (currentLvl + 1)*shiftSize);
                    if (!accu.contains(child))
                        accu.add(child);
                }
            }
            lvl.clear();
            lvl.addAll(accu);
            ++currentLvl;
            xPos = 0;
            accu.clear();
        }

        return background;
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
