package visualization;

import com.makerspace.Graph;
import com.makerspace.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class GraphImage<NodeT, NodeDrawer extends ObjDrawer<NodeT>> extends ObjDrawer<Graph<NodeT>>  {

    private NodeDrawer drawer;
    private int xShift;
    private int yShift;

    public GraphImage(int xCount, int yCount, int xShift, int yShift, NodeDrawer drw) {
        super((xCount - 1) * (drw.getWidth() + xShift),
                (yCount - 1) * (drw.getHeight() + yShift));

        this.xShift = xShift;
        this.yShift = yShift;
        drawer = drw;
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    }

    public GraphImage<NodeT, NodeDrawer> draw(Graph<NodeT> g) {
        Graphics2D graphics = image.createGraphics();
        int xPos = 0, nextLvlIndex = 1;

        HashMap<NodeT, Integer> currLevel = new HashMap<>();
        for (NodeT init : g.getLevel(0))
            currLevel.put(init, xPos++);
        xPos = 0;
        HashMap<NodeT, Integer> nextLevel = new HashMap<>();
        for (NodeT init : g.getLevel(nextLvlIndex))
            nextLevel.put(init, xPos++);

        while (!nextLevel.isEmpty()) {
            xPos = 0;
            final int currLvlIndex = nextLvlIndex - 1;
            currLevel.forEach((k, v) -> {
                graphics.drawImage(drawer.draw(k).getImage().orElse(
                        new BufferedImage(drawer.getWidth(), drawer.getHeight(),
                                BufferedImage.TYPE_BYTE_GRAY
                )), v*(drawer.getWidth() + xShift),
                        currLvlIndex * (drawer.getHeight() + yShift),
                        null);

                for (NodeT child : g.getChildren(k))
                    graphics.drawLine(v*(drawer.getWidth() + xShift) + xShift / 2,
                            currLvlIndex * (drawer.getHeight() + yShift) + drawer.getHeight(),
                            nextLevel.get(child)*(drawer.getWidth() + xShift) + xShift / 2,
                            (currLvlIndex + 1)*(drawer.getHeight() + yShift));

            });
            currLevel.clear();
            currLevel.putAll(nextLevel);
            nextLevel.clear();
            for (NodeT init : g.getLevel(++nextLvlIndex))
                nextLevel.put(init, xPos++);
        }
        int lastLvlIndex = nextLvlIndex - 1;
        currLevel.forEach((k, v) -> {
            graphics.drawImage(drawer.draw(k).getImage().orElse(
                    new BufferedImage(drawer.getWidth(), drawer.getHeight(),
                            BufferedImage.TYPE_BYTE_GRAY
                    )), v*(drawer.getWidth() + xShift),
                     lastLvlIndex * (drawer.getHeight() + yShift),
                    null);
        });


        return this;
    }

}
