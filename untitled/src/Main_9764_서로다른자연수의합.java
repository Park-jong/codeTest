import java.io.*;

public class Main_9764_서로다른자연수의합 {
    static int[][] dp;
    static int MOD = 100999;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        int[] array = new int[t];
        int max = 0;
        for (int i = 0; i < t; i++) {
            array[i] = Integer.parseInt(br.readLine());
            max = Math.max(max, array[i]);
        }
        solve(max);
        for (int i = 0; i < t; i++) {
            sb.append(dp[array[i]][array[i]]).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void solve(int max) {
        dp = new int[max + 1][max + 1];
        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= max; j++) {
                if (i > j) {
                    int remain = i - j;
                    dp[i][j] = (dp[i][j - 1] + dp[remain][j - 1]) % MOD;
                } else if (j == i) {
                    dp[i][i] = (dp[i][i - 1] + 1) % MOD;
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }

        }
    }
}