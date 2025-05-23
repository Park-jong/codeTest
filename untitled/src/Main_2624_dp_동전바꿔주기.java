import java.io.*;
import java.util.StringTokenizer;

public class Main_2624_dp_동전바꿔주기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        Coin[] coins = new Coin[k];

        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            coins[i] = new Coin(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        int[][] dp = new int[k + 1][t + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j <= t; j++) {
                int value = coins[i - 1].value;
                int maxCount = coins[i - 1].count;
                for (int useCount = 0, sum = j; useCount <= maxCount && sum >= 0; useCount++, sum -= value) {
                    dp[i][j] += dp[i - 1][sum];
                }
            }
        }
        bw.write(String.valueOf(dp[k][t]));
        bw.close();
        br.close();
    }

    static class Coin {
        int value;
        int count;

        Coin(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }
}