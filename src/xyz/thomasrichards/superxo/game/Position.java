package xyz.thomasrichards.superxo.game;

public enum Position {
    TL (0,0),
    TM (0,1),
    TR (0,2),
    ML (1,0),
    MM (1,1),
    MR (1,2),
    BL (2,0),
    BM (2,1),
    BR (2,2);

    private final int index;

    Position(int x, int y) {
        this.index = x * 3 + y;
    }

    public int getIndex() {
        return this.index;
    }

    public <T> T from(T[] m) {
        return m[this.index];
    }
}
