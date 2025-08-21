import java.io.*;

public class Main_17218_비밀번호만들기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String str1 = br.readLine();
        String str2 = br.readLine();
        String result = lcs(str1, str2);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String lcs(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return getLongestSequence(dp, str1, str2);
    }

    static String getLongestSequence(int[][] dp, String str1, String str2){
        int n = str1.length();
        int m = str2.length();
        char[] longest = new char[dp[n][m]];
        int i = n;
        int j = m;
        int index = dp[n][m] - 1;
        while (i > 0 && j > 0) {
            if (dp[i][j] == dp[i][j - 1]) {
                j--;
            } else if (dp[i][j] == dp[i - 1][j]) {
                i--;
            } else {
                longest[index--] = str1.charAt(i - 1);
                i--;
                j--;
            }
        }
        return String.valueOf(longest);
    }
}