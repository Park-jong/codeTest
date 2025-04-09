import java.io.*;
import java.util.StringTokenizer;

public class Main_18427_dp_함께블록쌓기 {
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
        boolean[][] possible = new boolean[n + 1][h + 1];
        dp[0][0] = 1;
        possible[0][0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= h; j++) {
                dp[i][j] = dp[i - 1][j];
                possible[i][j] = possible[i - 1][j];
                for (int k = 0; k < m; k++) {
                    if(students[i-1][k] == 0) break;
                    if (j - students[i - 1][k] >= 0 && possible[i - 1][j - students[i - 1][k]]) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - students[i - 1][k]]) % 10007;
                        possible[i][j] = true;
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