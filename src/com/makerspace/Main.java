package com.makerspace;

import visualization.FieldImage;
import visualization.GraphImage;

import java.util.Collection;

import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        FieldState empty = new FieldState();
	    Graph<FieldState> stateMap = new Graph<>(empty);

	    final Collection<FieldState> cachedX = FieldFactory.next(empty, CellState.X);
	    final Collection<FieldState> cachedO = FieldFactory.next(empty, CellState.O);

	    stateMap.addNodes(empty, cachedX);
	    boolean nextLevelInserted = true;
	    CellState player = CellState.X;

        while (nextLevelInserted) {

            player = player == CellState.X ? CellState.O : CellState.X;

            Collection<FieldState> lastLevel = stateMap.getLastLevel();
            int preInsertionDepth = stateMap.getDepth();

            for (FieldState fieldState : lastLevel) {
                 stateMap.addNodes(fieldState,
                        ((player == CellState.X) ? cachedX : cachedO)
                        .stream()
                        .filter(fieldState::compatible)
                        .map(fieldState::compose)
                        .collect(Collectors.toList())
                );
            }

            nextLevelInserted = stateMap.getDepth() > preInsertionDepth;
        }

        System.out.println(stateMap.getDepth());

        GraphImage<FieldState, FieldImage> image = new GraphImage<>(1600, 10,
                                                    80, 250, new FieldImage(20));
        image.draw(stateMap).save("graph.png");
    }
}
