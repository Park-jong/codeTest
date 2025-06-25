import java.io.*;
import java.util.*;

public class Main_20542_받아쓰기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        String answer = br.readLine();
        String correct = br.readLine();
        int result = getChangeCount(n, m, answer, correct);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getChangeCount(int n, int m, String answer, String correct) {
        return shortestChange(n, m, answer, correct);
    }

    static int shortestChange(int n, int m, String answer, String correct) {
        int[][] dp = new int[n + 1][m + 1];
        final int MAX_VALUE = Integer.MAX_VALUE >> 1;
        for (int i = 1; i <= m; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dp[i], MAX_VALUE);
            dp[i][0] = i;
            for (int j = 1; j <= m; j++) {
                char a = answer.charAt(i - 1);
                char c = correct.charAt(j - 1);
                if (isSameOrSimilar(a, c)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                }
                dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, dp[i][j]);
                dp[i][j] = Math.min(dp[i - 1][j] + 1, dp[i][j]);
                dp[i][j] = Math.min(dp[i][j - 1] + 1, dp[i][j]);

            }
        }
        return dp[n][m];
    }

    static boolean isSameOrSimilar(char a, char c) {
        return a == c || isSimilarI(a, c) || isSimilarV(a, c);
    }

    static boolean isSimilarI(char a, char c) {
        return a == 'i' && (c == 'j' || c == 'l');
    }

    static boolean isSimilarV(char a, char c) {
        return a == 'v' && c == 'w';
    }
}