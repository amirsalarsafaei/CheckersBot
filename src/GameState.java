import java.util.ArrayList;
import java.util.LinkedList;

public class GameState implements Comparable<GameState> {
    private final ArrayList<Coordinate> whites;
    private final ArrayList<Coordinate> whiteKings;
    private final ArrayList<Coordinate> blacks;
    private final ArrayList<Coordinate> blackKings;
    public int score = 0;
    public final char [][]table;
    public int steps;
    public Coordinate [][]coordinates;
    public Turn turn;
    public boolean neighboursCreated;
    public ArrayList<GameState> neighbours;
    public boolean playerTurnCanMove;

    public Coordinate playerTurnCanMoveCord;

    public Coordinate playerTurnCanMoveEndCord;
    boolean isValid(int x, int y) {
        return Math.max(x, y) < 8 && Math.min(x, y)>= 0;
    }

    private static final int []dy = {-1, 1, -1, 1}, dxKing = {1, 1, -1, -1}, dxWhite={-1, -1},
        dxBlack={1,1};
    public GameState(char [][]a, Turn turn, int steps) {
        this.steps = steps;
        this.turn =turn;
        whites = new ArrayList<>();
        whiteKings = new ArrayList<>();
        blacks = new ArrayList<>();
        blackKings = new ArrayList<>();
        table = cloneIt(a);
        coordinates = new Coordinate[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                coordinates[i][j] = new Coordinate(i, j);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (table[i][j] != 'E') {
                    if (table[i][j] == 'W' || table[i][j] == 'w') {
                        if (table[i][j] == 'w')
                            whites.add(coordinates[i][j]);
                        else
                            whiteKings.add(coordinates[i][j]);
                    }
                    else {
                        if (table[i][j] == 'b')
                            blacks.add(coordinates[i][j]);
                        else
                            blackKings.add(coordinates[i][j]);
                    }
                }
            }
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                    if (table[i][j] != 'E') {
//                        if (table[i][j] == 'W' || table[i][j] == 'w') {
//                            if (isValid(i-1, j-1) && isValid(i+1, j+1) && (blacks.contains(coordinates[i-1][j-1]) ||
//                                    blackKings.contains(coordinates[i-1][j-1]))) {
//                                threadedBy[i][j]++;
//                            }
//                            if (isValid(i+1, j+1) && isValid(i-1, j-1) && blackKings.contains(coordinates[i+1][j+1])) {
//                                threadedBy[i][j]++;
//                            }
//                            if (isValid(i-1,j+1) && isValid(i+1, j-1) && (blacks.contains(coordinates[i-1][j+1]) ||
//                                    blackKings.contains(coordinates[i-1][j+1])))
//                                threadedBy[i][j]++;
//                            if (isValid(i-1,j+1) && isValid(i+1, j-1) && blackKings.contains(coordinates[i+1][j-1]))
//                                threadedBy[i][j]++;
//                        }
//                        else {
//                            if (isValid(i-1, j-1) && isValid(i+1, j+1) && (whites.contains(coordinates[i+1][j+1]) ||
//                                    whiteKings.contains(coordinates[i+1][j+1]))) {
//                                threadedBy[i][j]++;
//                            }
//                            if (isValid(i+1, j+1) && isValid(i-1, j-1) && whiteKings.contains(coordinates[i-1][j-1])) {
//                                threadedBy[i][j]++;
//                            }
//                            if (isValid(i-1,j+1) && isValid(i+1, j-1) && (whites.contains(coordinates[i+1][j-1]) ||
//                                    whiteKings.contains(coordinates[i+1][j-1])) )
//                                threadedBy[i][j]++;
//                            if (isValid(i-1,j+1) && isValid(i+1, j-1) && whiteKings.contains(coordinates[i-1][j+1]))
//                                threadedBy[i][j]++;
//                        }
//                    }
//            }
//        }
        score += (blacks.size()  - whites.size()) * 1000L;
        score += (blackKings.size() - whiteKings.size()) * 1500L;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (table[i][j] != 'E') {
//                    if (table[i][j] == 'W' || table[i][j] == 'w') {
//                        score += threadedBy[i][j] * 500L;
//                    }
//                    else {
//                        score -= threadedBy[i][j] * 500L;
//                    }
//                }
//            }
//        }
        score -= steps * 50L;


    }

    public void endCreation() {
        playerTurnCanMove = false;
        if (Turn.White == turn) {
            for (Coordinate i : whites) {
                if (playerTurnCanMove)
                    break;
                for (int z = 0; z < 2; z++) {
                    if (playerTurnCanMove)
                        break;
                    int x = i.x + dxWhite[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E') {
                        playerTurnCanMoveCord = i;
                        playerTurnCanMoveEndCord = coordinates[x][y];
                        playerTurnCanMove = true;
                        break;
                    }
                    int xx = x + dxWhite[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'b' && table[x][y] != 'B')
                        continue;
                    playerTurnCanMoveCord = i;
                    playerTurnCanMoveEndCord = coordinates[xx][yy];
                    playerTurnCanMove = true;
                    break;
                }
            }
            for (Coordinate i : whiteKings) {
                if (playerTurnCanMove)
                    break;
                for (int z = 0; z < 4; z++) {
                    if (playerTurnCanMove)
                        break;
                    int x = i.x + dxKing[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E') {
                        playerTurnCanMoveCord = i;
                        playerTurnCanMoveEndCord = coordinates[x][y];
                        playerTurnCanMove = true;
                        break;
                    }
                    int xx = x + dxKing[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'b' && table[x][y] != 'B')
                        continue;
                    playerTurnCanMoveCord = i;
                    playerTurnCanMoveEndCord = coordinates[xx][yy];
                    playerTurnCanMove = true;
                    break;
                }
            }
        }
        else {
            for (Coordinate i : blacks) {
                if (playerTurnCanMove)
                    break;
                for (int z = 0; z < 2; z++) {
                    if (playerTurnCanMove)
                        break;
                    int x = i.x + dxBlack[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E') {
                        playerTurnCanMoveCord = i;
                        playerTurnCanMoveEndCord = coordinates[x][y];
                        playerTurnCanMove = true;
                        break;
                    }
                    int xx = x + dxBlack[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'w' && table[x][y] != 'W')
                        continue;
                    playerTurnCanMoveCord = i;
                    playerTurnCanMoveEndCord = coordinates[xx][yy];
                    playerTurnCanMove = true;
                    break;
                }
            }
            for (Coordinate i : blackKings) {
                if (playerTurnCanMove)
                    break;
                for (int z = 0; z < 4; z++) {
                    if (playerTurnCanMove)
                        break;
                    int x = i.x + dxKing[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E') {
                        playerTurnCanMoveCord = i;
                        playerTurnCanMoveEndCord = coordinates[x][y];
                        playerTurnCanMove = true;
                        break;
                    }
                    int xx = x + dxKing[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'w' && table[x][y] != 'W')
                        continue;
                    playerTurnCanMove = true;
                    playerTurnCanMoveEndCord = coordinates[xx][yy];
                    playerTurnCanMoveCord = i;
                    break;
                }
            }
        }
        if (getState() == States.Win) {
            score = Integer.MAX_VALUE;
        }
        if (getState() == States.Lose)
            score = Integer.MIN_VALUE;
    }

    public void createNeighbours() {
        neighbours = new ArrayList<>();
        if (Turn.White == turn) {
            LinkedList<NextMoveGameState> gameStates = new LinkedList<>();
            for (Coordinate i : whites) {
                for (int z = 0; z < 2; z++) {
                    int x = i.x + dxWhite[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E'){
                        char [][]tmp = cloneIt(table);
                        tmp[i.x][i.y] = 'E';
                        tmp[x][y] = 'w';
                        if (x == 0){
                            tmp[x][y] = 'W';
                        }
                        gameStates.add(new NextMoveGameState(tmp, x, y, false, turn, steps+1));
                    }
                    int xx = x + dxWhite[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'b' && table[x][y] != 'B')
                        continue;
                    char [][]tmp = cloneIt(table);
                    tmp[i.x][i.y] = 'E';
                    tmp[x][y] ='E';
                    tmp[xx][yy] = 'w';
                    boolean jumped = true;
                    if (xx == 0){
                        tmp[xx][yy] = 'W';
                        //jumped = false;
                    }
                    gameStates.add(new NextMoveGameState(tmp, xx, yy, jumped, turn, steps+1));
                }
            }
            for (Coordinate i : whiteKings) {
                for (int z = 0; z < 4; z++) {
                    int x = i.x + dxKing[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E'){
                        char [][]tmp = cloneIt(table);
                        tmp[i.x][i.y] = 'E';
                        tmp[x][y] = 'W';
                        gameStates.add(new NextMoveGameState(tmp, x, y, false, turn, steps+1));
                    }
                    int xx = x + dxKing[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'b' && table[x][y] != 'B')
                        continue;
                    char [][]tmp = cloneIt(table);
                    tmp[i.x][i.y] = 'E';
                    tmp[x][y] ='E';
                    tmp[xx][yy] = 'W';
                    gameStates.add(new NextMoveGameState(tmp, xx, yy, true, turn, steps+1));
                }
            }
            while(!gameStates.isEmpty()) {
                NextMoveGameState i = gameStates.poll();
                neighbours.add(i.gameState);
                if (!i.jumped)
                    continue;
                if (i.gameState.whites.contains(i.MovedPieceLoc)) {
                    for (int z = 0; z < 2; z++) {
                        int x = i.MovedPieceLoc.x + dxWhite[z], y = i.MovedPieceLoc.y + dy[z];
                        if (!isValid(x, y))
                            continue;
                        if (i.gameState.table[x][y] == 'E'){
                            continue;
                        }
                        int xx = x + dxWhite[z], yy = y + dy[z];
                        if (!isValid(xx, yy))
                            continue;
                        if (i.gameState.table[xx][yy] != 'E')
                            continue;
                        if (i.gameState.table[x][y] != 'b' && i.gameState.table[x][y] != 'B')
                            continue;
                        char [][]tmp = cloneIt(i.gameState.table);
                        tmp[i.MovedPieceLoc.x][i.MovedPieceLoc.y] = 'E';
                        tmp[x][y] ='E';
                        tmp[xx][yy] = 'w';
                        boolean jumped = true;
                        if (xx == 0){
                            //jumped = false;
                            tmp[xx][yy] = 'W';
                        }
                        boolean inlist = false;
                        for (NextMoveGameState j : gameStates) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.gameState.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        for (GameState j : neighbours) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        if (!inlist)
                            gameStates.add(new NextMoveGameState(tmp, xx, yy, jumped, turn, steps+1));
                    }
                }
                else {
                    for (int z = 0; z < 4; z++) {
                        int x = i.MovedPieceLoc.x + dxKing[z], y = i.MovedPieceLoc.y + dy[z];
                        if (!isValid(x, y))
                            continue;
                        if (i.gameState.table[x][y] == 'E'){
                            continue;
                        }
                        int xx = x + dxKing[z], yy = y + dy[z];
                        if (!isValid(xx, yy))
                            continue;
                        if (i.gameState.table[xx][yy] != 'E')
                            continue;
                        if (i.gameState.table[x][y] != 'b' && i.gameState.table[x][y] != 'B')
                            continue;
                        char [][]tmp = cloneIt(i.gameState.table);
                        tmp[i.MovedPieceLoc.x][i.MovedPieceLoc.y] = 'E';
                        tmp[x][y] ='E';
                        tmp[xx][yy] = 'W';
                        boolean inlist = false;
                        for (NextMoveGameState j : gameStates) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.gameState.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        for (GameState j : neighbours) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        if (!inlist)
                            gameStates.add(new NextMoveGameState(tmp, xx, yy, true, turn, steps+1));
                    }
                }
            }
        }
        else {
            LinkedList<NextMoveGameState> gameStates = new LinkedList<>();
            for (Coordinate i : blacks) {
                for (int z = 0; z < 2; z++) {
                    int x = i.x + dxBlack[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E'){
                        char [][]tmp = cloneIt(table);
                        tmp[i.x][i.y] = 'E';
                        tmp[x][y] = 'b';
                        if (x == 7){
                            tmp[x][y] = 'B';
                        }
                        gameStates.add(new NextMoveGameState(tmp, x, y, false, turn, steps+1));
                    }
                    int xx = x + dxBlack[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'w' && table[x][y] != 'W')
                        continue;
                    char [][]tmp = cloneIt(table);
                    tmp[i.x][i.y] = 'E';
                    tmp[x][y] ='E';
                    tmp[xx][yy] = 'b';
                    boolean jumped = true;
                    if (xx == 7){
                        tmp[xx][yy] = 'B';
                        //jumped = false;
                    }
                    gameStates.add(new NextMoveGameState(tmp, xx, yy, jumped, turn, steps+1));
                }
            }
            for (Coordinate i : blackKings) {
                for (int z = 0; z < 4; z++) {
                    int x = i.x + dxKing[z], y = i.y + dy[z];
                    if (!isValid(x, y))
                        continue;
                    if (table[x][y] == 'E'){
                        char [][]tmp = cloneIt(table);
                        tmp[i.x][i.y] = 'E';
                        tmp[x][y] = 'B';
                        gameStates.add(new NextMoveGameState(tmp, x, y, false, turn, steps+1));
                    }
                    int xx = x + dxKing[z], yy = y + dy[z];
                    if (!isValid(xx, yy))
                        continue;
                    if (table[xx][yy] != 'E')
                        continue;
                    if (table[x][y] != 'w' && table[x][y] != 'W')
                        continue;
                    char [][]tmp = cloneIt(table);
                    tmp[i.x][i.y] = 'E';
                    tmp[x][y] ='E';
                    tmp[xx][yy] = 'B';
                    gameStates.add(new NextMoveGameState(tmp, xx, yy, true, turn, steps+1));
                }
            }
            while(!gameStates.isEmpty()) {
                NextMoveGameState i = gameStates.poll();
                neighbours.add(i.gameState);
                if (!i.jumped)
                    continue;
                if (i.gameState.blacks.contains(i.MovedPieceLoc)) {
                    for (int z = 0; z < 2; z++) {
                        int x = i.MovedPieceLoc.x + dxBlack[z], y = i.MovedPieceLoc.y + dy[z];
                        if (!isValid(x, y))
                            continue;
                        if (i.gameState.table[x][y] == 'E'){
                            continue;
                        }
                        int xx = x + dxBlack[z], yy = y + dy[z];
                        if (!isValid(xx, yy))
                            continue;
                        if (i.gameState.table[xx][yy] != 'E')
                            continue;
                        if (i.gameState.table[x][y] != 'w' && i.gameState.table[x][y] != 'W')
                            continue;
                        char [][]tmp = cloneIt(i.gameState.table);
                        tmp[i.MovedPieceLoc.x][i.MovedPieceLoc.y] = 'E';
                        tmp[x][y] ='E';
                        tmp[xx][yy] = 'b';
                        boolean jumped = true;
                        if (xx == 7){
                            //jumped = false;
                            tmp[xx][yy] = 'B';
                        }
                        boolean inlist = false;
                        for (NextMoveGameState j : gameStates) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.gameState.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        for (GameState j : neighbours) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        if (!inlist)
                            gameStates.add(new NextMoveGameState(tmp, xx, yy, jumped, turn, steps+1));
                    }
                }
                else {
                    for (int z = 0; z < 4; z++) {
                        int x = i.MovedPieceLoc.x + dxKing[z], y = i.MovedPieceLoc.y + dy[z];
                        if (!isValid(x, y))
                            continue;
                        if (i.gameState.table[x][y] == 'E'){
                            continue;
                        }
                        int xx = x + dxKing[z], yy = y + dy[z];
                        if (!isValid(xx, yy))
                            continue;
                        if (i.gameState.table[xx][yy] != 'E')
                            continue;
                        if (i.gameState.table[x][y] != 'w' && i.gameState.table[x][y] != 'W')
                            continue;
                        char [][]tmp = cloneIt(i.gameState.table);
                        tmp[i.MovedPieceLoc.x][i.MovedPieceLoc.y] = 'E';
                        tmp[x][y] ='E';
                        tmp[xx][yy] = 'B';
                        boolean inlist = false;
                        for (NextMoveGameState j : gameStates) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.gameState.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        for (GameState j : neighbours) {
                            boolean same = true;
                            for (int ii = 0; ii < 8 && same; ii++)
                                for (int jj = 0; jj < 8; jj++)
                                    if (j.table[ii][jj] != tmp[ii][jj]) {
                                        same = false;
                                        break;
                                    }
                            if (same)
                                inlist = true;
                        }
                        if (!inlist)
                            gameStates.add(new NextMoveGameState(tmp, xx, yy, true, turn, steps+1));
                    }
                }
            }
        }
        for (GameState gameState : neighbours) {
            if (gameState.turn == Turn.White)
                gameState.turn = Turn.Black;
            else
                gameState.turn = Turn.White;
            gameState.endCreation();
        }
        neighboursCreated = true;
    }


    @Override
    public int compareTo(GameState o) {
        return Long.compare(score, o.score);
    }

    public States getState() {

        if (blacks.size() + blackKings.size() == 0)
            return States.Lose;
        if (whites.size() + whiteKings.size() == 0)
            return States.Win;
        if (!playerTurnCanMove) {
            if (turn == Turn.Black)
                return States.Lose;
            else
                return States.Win;
        }
        return States.Pending;
    }

    public void print() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(table[i][j]);
            }
            System.out.println();
        }
    }

    public char[][] cloneIt(char[][] toBeCopied) {
        char [][] res = new char[toBeCopied.length][toBeCopied[0].length];
        for (int i = 0; i < toBeCopied.length; i++)
            System.arraycopy(toBeCopied[i], 0, res[i], 0, toBeCopied[i].length);
        return res;
    }
}
