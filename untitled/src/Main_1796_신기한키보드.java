import java.io.*;
import java.util.Arrays;

public class Main_1796_신기한키보드 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String s = br.readLine();
        int result = findMinPressButton(s);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinPressButton(String s) {
        char[] c = s.toCharArray();
        int n = c.length;
        final int MAX_VALUE = Integer.MAX_VALUE >> 1;
        Arrays.sort(c);
        int[][] dp = new int[n + 1][n];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], MAX_VALUE);
        }
        dp[0][0] = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            count++;
            if (i == n - 1 || c[i] != c[i + 1]) {
                int max = -1;
                int min = n;
                for (int j = 0; j < n; j++) {
                    if (s.charAt(j) == c[i]) {
                        max = Math.max(j, max);
                        min = Math.min(j, min);
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (dp[i][j] != MAX_VALUE) {
                        dp[i + 1][max] = Math.min(dp[i + 1][max], dp[i][j] + Math.abs(j - min) + Math.abs(max - min) + count);
                        dp[i + 1][min] = Math.min(dp[i + 1][min], dp[i][j] + Math.abs(j - max) + Math.abs(max - min) + count);
                    }
                }
                count = 0;
            } else {
                for (int j = 0; j < n; j++) {
                    dp[i + 1][j] = dp[i][j];
                }
            }
        }
        int result = MAX_VALUE;
        for (int i = 0; i < n; i++) {
            result = Math.min(result, dp[n][i]);
        }
        return result;
    }
}