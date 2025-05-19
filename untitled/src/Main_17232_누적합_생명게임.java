import java.io.*;
import java.util.StringTokenizer;

public class Main_17232_누적합_생명게임 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        int[][] board = new int[n][m];
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                if (row.charAt(j) == '*') board[i][j] = 1;
            }
        }

        for (int step = 0; step < t; step++) {
            int[][] sum = new int[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + board[i - 1][j - 1];
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int xLow = Math.max(j - k, 0);
                    int xHigh = Math.min(j + k, m - 1);
                    int yLow = Math.max(i - k, 0);
                    int yHigh = Math.min(i + k, n - 1);
                    int center = board[i][j];
                    int rangeSum = sum[yHigh + 1][xHigh + 1] - sum[yLow][xHigh + 1] - sum[yHigh + 1][xLow] + sum[yLow][xLow] - center;
                    if (center == 1 && rangeSum >= a && rangeSum <= b) board[i][j] = 1;
                    else if (center == 0 && rangeSum > a && rangeSum <= b) board[i][j] = 1;
                    else board[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(board[i][j] == 0 ? "." : "*");
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}