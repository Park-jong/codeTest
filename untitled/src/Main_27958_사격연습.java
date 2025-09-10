import java.io.*;
import java.util.StringTokenizer;

public class Main_27958_사격연습 {

    static int n;
    static int k;
    static int[][] board;
    static int[][] scores;
    static int[] power;
    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        board = new int[n][n];
        scores = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                scores[i][j] = board[i][j];
            }
        }
        power = new int[k];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            power[i] = Integer.parseInt(st.nextToken());
        }
        dfs(0, 0);
        bw.write(String.valueOf(max));
        bw.close();
        br.close();
    }

    static void dfs(int depth, int score) {
        if (depth == k) {
            max = Math.max(score, max);
            return;
        }
        for (int i = 0; i < n; i++) {
            int x = i;
            int y = -1;
            int uBoard = 0;
            int dBoard = 0;
            int lBoard = 0;
            int rBoard = 0;
            int uScore = 0;
            int dScore = 0;
            int lScore = 0;
            int rScore = 0;
            int prev = 0;
            int prevScore = 0;
            for (int j = 0; j < n; j++) {
                if (board[i][j] > 0) {
                    prev = board[i][j];
                    prevScore = scores[i][j];
                    y = j;
                    break;
                }
            }
            if (y == -1) {
                dfs(depth + 1, score);
                continue;
            }
            if (x > 0) {
                uBoard = board[x - 1][y];
                uScore = scores[x - 1][y];
            }
            if (x < n - 1) {
                dBoard = board[x + 1][y];
                dScore = scores[x + 1][y];
            }
            if (y > 0) {
                lBoard = board[x][y - 1];
                lScore = scores[x][y - 1];
            }
            if (y < n - 1) {
                rBoard = board[x][y + 1];
                rScore = scores[x][y + 1];
            }
            if (board[x][y] <= power[depth] || scores[x][y] >= 10) {
                board[x][y] = 0;
                scores[x][y] = 0;
                if (prevScore < 10) {
                    if (x > 0 && board[x - 1][y] == 0) {
                        board[x - 1][y] = prev / 4;
                        scores[x - 1][y] = prev / 4;
                    }
                    if (x < n - 1 && board[x + 1][y] == 0) {
                        board[x + 1][y] = prev / 4;
                        scores[x + 1][y] = prev / 4;
                    }
                    if (y > 0 && board[x][y - 1] == 0) {
                        board[x][y - 1] = prev / 4;
                        scores[x][y - 1] = prev / 4;
                    }
                    if (y < n - 1 && board[x][y + 1] == 0) {
                        board[x][y + 1] = prev / 4;
                        scores[x][y + 1] = prev / 4;
                    }
                }
                dfs(depth + 1, score + prevScore);
            } else {
                board[x][y] -= power[depth];
                dfs(depth + 1, score);
            }
            if (x > 0) {
                board[x - 1][y] = uBoard;
                scores[x - 1][y] = uScore;
            }
            if (x < n - 1) {
                board[x + 1][y] = dBoard;
                scores[x + 1][y] = dScore;
            }
            if (y > 0) {
                board[x][y - 1] = lBoard;
                scores[x][y - 1] = lScore;
            }
            if (y < n - 1) {
                board[x][y + 1] = rBoard;
                scores[x][y + 1] = rScore;
            }
            board[x][y] = prev;
            scores[x][y] = prevScore;
        }
    }
}