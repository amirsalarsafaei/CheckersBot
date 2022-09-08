import java.io.*;
import java.util.Scanner;

public class Main {
    static File inputFile, outputFile;
    public static void main(String[] args) {
        inputFile = new File("C:\\Users\\Amirsalar\\Documents\\old Documents\\CheckersBot\\inputAndOutput\\input.txt");
        outputFile = new File("C:\\Users\\Amirsalar\\Documents\\old Documents\\CheckersBot\\inputAndOutput\\output.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int a = Integer.parseInt(scanner.nextLine());
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(outputFile));
            for (int z = 0; z < a; z++) {
                char[][] ch = new char[8][8];
                String[] s = new String[8];
                for (int i = 0; i < 8; i++)
                    s[i] = scanner.nextLine();

                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++)
                        ch[i][j] = s[i].charAt(j);
                MiniMax miniMax = new MiniMax();
                GameState gameState = new GameState(ch, Turn.Black, 0);
                gameState.endCreation();
                int avgDepth = 0;
                for (int i = 0; i < 30; i++)
                    avgDepth += miniMax.findDepth(1000000, gameState);
                avgDepth /= 30;
                System.out.println(avgDepth);
                if (avgDepth % 2 == 1)
                    avgDepth--;
//                System.out.println("found depth");
//                System.out.println(avgDepth);

                miniMax.minimax(gameState, Turn.Black,6, Integer.MIN_VALUE, Integer.MAX_VALUE,true);

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++)
                            printStream.print(miniMax.ans.table[i][j]);
                        printStream.println();
                    }
                    printStream.println();


            }
            printStream.close();
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
