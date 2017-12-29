package com.makerspace;

import java.util.Objects;
import java.util.stream.IntStream;

public class FieldState implements Cloneable {
    private short xPos;
    private short oPos;
    private static final int[] winMask =
            {7, 56, 448,    // horizontal lines from top to bottom
            73, 146, 292,   // vertical lines from left to right
            84, 273};       // diagonals (left_down - right_up, left_up - right_down)

    public FieldState() {
        xPos = 0;
        oPos = 0;
    }

    private FieldState(int x, int o) {
        xPos = (short) x;
        oPos = (short) o;
    }

    public FieldState(int n, CellState cs) {
        this();
        set(n, cs);
    }

    public FieldState set(int n, CellState cs) {
        switch (cs) {
            case None:
                xPos &= ~(1 << n);
                oPos &= ~(1 << n);
                break;
            case X:
                xPos |= (1 << n);
                oPos &= ~(1 << n);
                break;
            case O:
                xPos &= ~(1 << n);
                oPos |= (1 << n);
        }
        return this;
    }

    public boolean hasSet(int pos) {
        return ((xPos | oPos) & (1 << pos)) > 0;
    }

    public CellState getCell(int n) {
        if ((xPos & (1 << n)) > 0)
            return CellState.X;
        else if ((oPos & (1 << n)) > 0)
            return CellState.O;
        else
            return CellState.None;
    }

    CellState getPlayer() {
        int xSum = 0, oSum = 0;
        for (int i = 0; i < 9; i++) {
            xSum += ((xPos >>> i) & 1);
            oSum += ((oPos >>> i) & 1);
        }
        return xSum > oSum ? CellState.O : CellState.X;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldState that = (FieldState) o;
        return xPos == that.xPos &&
                oPos == that.oPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, oPos);
    }

    @Override
    public String toString() {
        StringBuilder accu = new StringBuilder();
        for (int i = 1; i != (1 << 9); i <<= 1) {
            if ((xPos & i) > 0)
                accu.append('x');
            else if ((oPos & i) > 0)
                accu.append('o');
            else
                accu.append('-');
        }
        return accu.toString();
    }

    public boolean isWon() {
        return IntStream.of(winMask)
                .anyMatch(mask ->
                        ( ((xPos & mask) == mask) || ((oPos & mask) == mask) )
                );
    }

    FieldState compose(int pos, CellState toSet)  {
        try {
            return ((FieldState) this.clone()).set(pos, toSet);
        } catch (CloneNotSupportedException e) {
            System.err.println("Cast failed");
            return this;
        }
    }

    FieldState compose(FieldState that) {
        return new FieldState(xPos | that.xPos, oPos | that.oPos);
    }

    boolean compatible(FieldState next) {
        return !isWon() && (((this.oPos | this.xPos) & (next.oPos | next.xPos)) == 0);
    }
}
