public class NextMoveGameState {
    GameState gameState;
    Coordinate MovedPieceLoc;
    boolean jumped;
    public NextMoveGameState(char [][]table, int x, int y, boolean jumped, Turn turn, int steps) {
        this.jumped = jumped;
        gameState = new GameState(table, turn, steps);
        MovedPieceLoc = gameState.coordinates[x][y];
    }
}
