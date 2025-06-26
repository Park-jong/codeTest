import java.io.*;
import java.util.*;

public class Main_15483_최소편집 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String str1 = br.readLine();
        String str2 = br.readLine();
        int result = changeStringCount(str1, str2);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int changeStringCount(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= m; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
            for (int j = 1; j <= m; j++) {
                fillArray(dp, i, j, str1, str2);
            }
        }
        return dp[n][m];
    }

    static void fillArray(int[][] dp, int i, int j, String str1, String str2) {
        if (isEqual(str1.charAt(i - 1), str2.charAt(j - 1))) {
            dp[i][j] = dp[i - 1][j - 1];
        } else {
            dp[i][j] = dp[i - 1][j - 1] + 1;
        }
        dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
    }

    static boolean isEqual(char a, char b) {
        return a == b;
    }
}