import java.io.*;
import java.util.StringTokenizer;

public class Main_20047_동전옮기기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String s = br.readLine();
        String t = br.readLine();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int i = Integer.parseInt(st.nextToken());
        int j = Integer.parseInt(st.nextToken());
        String result = isPossible(n, s, t, i, j) ? "YES" : "NO";
        bw.write(result);
        bw.close();
        br.close();
    }

    static boolean isPossible(int n, String s, String t, int i1, int i2) {
        char[] sChar = new char[n];
        char[] tChar = t.toCharArray();
        for (int i = 0, index = 0; i < n; i++) {
            if (i == i1) {
                sChar[n - 2] = s.charAt(i1);
            } else if (i == i2) {
                sChar[n - 1] = s.charAt(i2);
            } else {
                sChar[index++] = s.charAt(i);
            }
        }
        boolean[][] dp = new boolean[n + 1][3];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0 && i <= n - 2 && dp[i - 1][j] && sChar[i - 1] == tChar[i - 1]) {
                    dp[i][j] = true;
                }
                if (j == 1 && i <= n - 1) {
                    if ((dp[i - 1][j - 1] && sChar[n - 2] == tChar[i - 1]) || (i > 1 && dp[i - 1][j] && sChar[i - 2] == tChar[i - 1])) {
                        dp[i][j] = true;
                    }
                }
                if (j == 2) {
                    if ((dp[i - 1][j - 1] && sChar[n - 1] == tChar[i - 1]) || (i > 2 && dp[i - 1][j] && sChar[i - 3] == tChar[i - 1])) {
                        dp[i][j] = true;
                    }
                }
            }
        }
        return dp[n][2];
    }
}