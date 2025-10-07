import java.io.*;
import java.util.*;

public class Main {

    static long[][] dp = new long[1_000_001][2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        fillDP();
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            sb.append(solve(n)).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void fillDP() {
        int n = 1000000;
        int MOD = 1_000_000_007;
        dp[1][0] = 7;
        dp[1][1] = 3;
        for (int i = 2; i <= n; i++) {
            dp[i][0] = (dp[i - 1][0] * 3 + dp[i - 1][1] * 4) % MOD;
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][1] * 2) % MOD;
        }
    }

    static int solve(int n) {
        return (int) dp[n][0];
    }
}