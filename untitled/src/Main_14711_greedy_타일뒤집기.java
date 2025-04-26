import java.io.*;

public class Main_14711_greedy_타일뒤집기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        String firstRow = br.readLine();
        boolean[][] board = new boolean[n][n];
        boolean[][] result = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            if (firstRow.charAt(i) == '#') {
                if (i > 0) board[0][i - 1] = !board[0][i - 1];
                if (i < n - 1) board[0][i + 1] = !board[0][i + 1];
                if (n > 1) board[1][i] = true;
                result[0][i] = true;
            }
        }
        changeLines(n, board, result);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(result[i][j] ? "#" : ".");
            }
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void changeLines(int n, boolean[][] board, boolean[][] result) {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i - 1][j]) {
                    if (j > 0) board[i][j - 1] = !board[i][j - 1];
                    if (j < n - 1) board[i][j + 1] = !board[i][j + 1];
                    if (i < n - 1) board[i + 1][j] = !board[i + 1][j];
                    result[i][j] = true;
                }
            }
        }
    }
}