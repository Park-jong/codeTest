import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_15924_욱제는사과팬이야 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][m];
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = str.charAt(j);
            }
        }
        int result = findCount(n, m, map);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findCount(int n, int m, char[][] map) {
        int[][] dp = new int[n + 1][m + 1];
        int MOD = 1_000_000_009;
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], 1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'E') {
                    dp[i][j + 1] = (dp[i][j + 1] + dp[i][j]) % MOD;
                } else if (map[i][j] == 'S') {
                    dp[i + 1][j] = (dp[i + 1][j] + dp[i][j]) % MOD;
                } else if (map[i][j] == 'B') {
                    dp[i][j + 1] = (dp[i][j + 1] + dp[i][j]) % MOD;
                    dp[i + 1][j] = (dp[i + 1][j] + dp[i][j]) % MOD;
                }
            }
        }
        return dp[n - 1][m - 1];
    }
}