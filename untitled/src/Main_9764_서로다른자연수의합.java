import java.io.*;

public class Main_9764_서로다른자연수의합 {
    static int[][] dp;
    static int MOD = 100999;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        solve();
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            sb.append(get(n)).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void solve() {
        dp = new int[2001][2001];
        for (int i = 1; i <= 2000; i++) {
            dp[i][i] = 1;
            for (int j = i - 1; j > 0; j--) {
                int remain = i - j;
                int sum = 0;
                for (int k = 1; k < j && k <= remain; k++) {
                    sum = (sum + dp[remain][k]) % MOD;
                }
                dp[i][j] = sum;
            }
        }
    }

    static int get(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result = (result + dp[n][i]) % MOD;
        }
        return result;
    }
}