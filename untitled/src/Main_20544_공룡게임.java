import java.io.*;

public class Main_20544_공룡게임 {

    static final int DIVIDE = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int result = getCountOfNumber(n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getCountOfNumber(int n) {
        int[][] dp = new int[n + 1][3];
        int[][] no2Dp = new int[n + 1][3];
        dp[1][0] = 1;
        no2Dp[1][0] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i][0] = ((dp[i - 1][0] + dp[i - 1][1]) % DIVIDE + dp[i - 1][2]) % DIVIDE;
            dp[i][1] = dp[i - 1][0] * 2 % DIVIDE;
            dp[i][2] = (dp[i - 1][1] + dp[i - 2][0]) % DIVIDE;
            no2Dp[i][0] = ((no2Dp[i - 1][0] + no2Dp[i - 1][1]) % DIVIDE + no2Dp[i - 1][2]) % DIVIDE;
            no2Dp[i][1] = no2Dp[i - 1][0];
            no2Dp[i][2] = no2Dp[i - 1][1];
        }
        int total = ((dp[n][0] + dp[n][1]) % DIVIDE + dp[n][2]) % DIVIDE;
        int no2Total  = ((no2Dp[n][0] + no2Dp[n][1]) % DIVIDE + no2Dp[n][2]) % DIVIDE;
        if(total < no2Total){
            total += DIVIDE;
        }
        return total - no2Total;
    }
}