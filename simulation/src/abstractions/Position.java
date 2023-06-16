package abstractions;

import java.util.Objects;

public class Position {
    private final int hash_code;
    public int x;
    public int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
        this.hash_code = Objects.hash(x, y);
    }

    public Position(Position pos){
        this.x = pos.x;
        this.y = pos.y;
        this.hash_code = pos.hash_code;
    }

    @Override
    public boolean equals(Object pos) {
        if (this == pos)
            return true;
        if (pos == null || getClass() != pos.getClass())
            return false;
        Position that = (Position) pos;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return this.hash_code;
    }

    public int calc_distance(Position pos){
        return Math.max(Math.abs(this.x - pos.x), Math.abs(this.y - pos.y));
    }
}
