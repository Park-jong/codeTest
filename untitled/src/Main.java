import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int[][] students = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int j = 0;
            while (st.hasMoreTokens()) {
                students[i][j++] = Integer.parseInt(st.nextToken());
            }
        }
        int[][] dp = new int[n + 1][h + 1];
        boolean[][] posible = new boolean[n + 1][h + 1];
        dp[0][0] = 1;
        posible[0][0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= h; j++) {
                dp[i][j] = dp[i - 1][j];
                posible[i][j] = posible[i - 1][j];
                for (int k = 0; k < m; k++) {
                    if(students[i-1][k] == 0) break;
                    if (j - students[i - 1][k] >= 0 && posible[i - 1][j - students[i - 1][k]]) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - students[i - 1][k]]) % 10007;
                        posible[i][j] = true;
                    }
                }
            }
        }
        bw.write(String.valueOf(dp[n][h]));
        bw.flush();
        bw.close();
        br.close();
    }
}