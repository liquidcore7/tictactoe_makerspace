package com.makerspace;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FieldFactory {

    public static Collection<FieldState> next(FieldState previous, CellState player) {
        List<FieldState> variants = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            if (!previous.hasSet(i)) {
                variants.add(previous.compose(i, player));
            }
        }
        return variants;
    }
}
