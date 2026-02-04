import java.io.*;
import java.util.StringTokenizer;

public class Main_2615_오목 {
    static int lineCount = 19;
    static int[][] board = new int[lineCount][lineCount];
    static boolean[][][] isVisited = new boolean[4][lineCount][lineCount];
    static int[] dx = {1, 1, 0, 1};
    static int[] dy = {0, 1, 1, -1};
    static int resultD = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < lineCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < lineCount; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        String result = checkGameResult();
        bw.write(result);
        bw.close();
        br.close();
    }

    static String checkGameResult() {
        int result = 0;
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineCount; j++) {
                if (board[i][j] != 0) {
                    result = checkPosition(i, j);
                    if (result != 0) {
                        if (resultD == 3) {
                            return String.format("%d\n%d %d", result, i + 5, j - 3);
                        }
                        return String.format("%d\n%d %d", result, i + 1, j + 1);
                    }
                }
            }
        }
        return "0";
    }

    static int checkPosition(int x, int y) {
        int player = board[x][y];
        for (int i = 0; i < 4; i++) {
            if (isVisited[i][x][y]) continue;
            int nx = dx[i] + x;
            int ny = dy[i] + y;
            if (ny >= 0 && nx < lineCount && ny < lineCount && board[nx][ny] == player) {
                int count = checkDirection(1, i, x, y);
                if (count == 5) {
                    resultD = i;
                    return player;
                }
            }
        }
        return 0;
    }

    static int checkDirection(int depth, int d, int x, int y) {
        isVisited[d][x][y] = true;
        int nx = dx[d] + x;
        int ny = dy[d] + y;
        if (ny >= 0 && nx < lineCount && ny < lineCount && board[nx][ny] == board[x][y]) {
            return checkDirection(depth + 1, d, nx, ny);
        } else {
            return depth;
        }
    }
}