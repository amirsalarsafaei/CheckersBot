import java.util.Random;

public class MiniMax {
    GameState ans;
    public int findDepth(int cnt, GameState gameState) {
        int h = 0, tmp = 1;
        GameState last = gameState;
        Random random = new Random(System.currentTimeMillis());
        while(tmp <= cnt && last.getState() == States.Pending) {
            if (!last.neighboursCreated) {
                last.createNeighbours();
            }
            tmp *= last.neighbours.size();
            h++;
            last = gameState.neighbours.get(random.nextInt(gameState.neighbours.size()));
        }
        return h-1;
    }
    public int minimax(GameState gameState, Turn turn, int depth_left, int alpha, int beta, boolean root) {
        //System.out.println(depth_left + " " + turn + " " + alpha + " " + beta + " " + gameState.score);
        if (depth_left == 0)
            return gameState.score;
        if (turn == Turn.Black) { //Maximizing
            if (!gameState.neighboursCreated) {
                gameState.createNeighbours();
            }
            int tmp = Integer.MIN_VALUE;
            GameState res = null;
            for (GameState i : gameState.neighbours) {
                int tmp2 = minimax(i, Turn.White, depth_left - 1,alpha, beta, false);
                if (tmp2 >= tmp) {
                    res = i;
                    tmp = tmp2;
                }
                alpha = Math.max(alpha, tmp2);
                if (beta <= alpha) {
                    break;
                }
            }
            if (root)
                ans = res;
            return tmp;
        }
        else {//Minimizing
            if (!gameState.neighboursCreated) {
                gameState.createNeighbours();
            }
            int tmp = Integer.MAX_VALUE;
            GameState res = null;
            for (GameState i : gameState.neighbours) {
                int tmp2 = minimax(i, Turn.Black, depth_left - 1,alpha, beta, false);
                if (tmp2 <= tmp) {
                    res = i;
                    tmp = tmp2;
                }
                beta = Math.min(tmp2, beta);
                if (beta <= alpha)
                    break;
            }
            if (root)
                ans = res;
            return tmp;
        }
    }
}
