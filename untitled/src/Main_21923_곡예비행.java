import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_21923_곡예비행 {

    static final int MIN_VALUE = Integer.MIN_VALUE / 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] area = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                area[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = maxAreaSum(n, m, area);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int maxAreaSum(int n, int m, int[][] area) {
        int[][] up = up(n, m, area);
        int[][] down = down(n, m, area);
        return areaSum(n, m, up, down);
    }

    static int[][] up(int n, int m, int[][] area) {
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], MIN_VALUE);
        }
        dp[n][1] = 0;
        dp[n - 1][0] = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]) + area[i][j - 1];
            }
        }
        return dp;
    }

    static int[][] down(int n, int m, int[][] area) {
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], MIN_VALUE);
        }
        dp[n][m - 1] = 0;
        dp[n - 1][m] = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]) + area[i][j];
            }
        }
        return dp;
    }

    static int areaSum(int n, int m, int[][] up, int[][] down) {
        int max = MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max = Math.max(max, up[i][j + 1] + down[i][j]);
            }
        }
        return max;
    }
}