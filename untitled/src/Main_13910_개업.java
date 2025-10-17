import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_13910_개업 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] s = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMinOrder(n, m, s);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinOrder(int n, int m, int[] s) {
        int[][] dp = new int[m + 1][n + 1];
        int MAX_VALUE = Integer.MAX_VALUE >> 1;
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], MAX_VALUE);
            dp[i][0] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - s[i - 1] >= 0) {
                    dp[i][j] = Math.min(Math.min(dp[i][j], dp[i - 1][j - s[i - 1]]), dp[i][j - s[i - 1]] + 1);
                }
            }
        }
        return dp[m][n] != MAX_VALUE ? dp[m][n] : -1;
    }
}