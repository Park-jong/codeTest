import java.io.*;

public class Main_2302_극장좌석 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] array = countOfNumber(n);
        int prev = 0;
        int result = 1;
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            int vip = Integer.parseInt(br.readLine());
            result *= array[vip - prev - 1];
            prev = vip;
        }
        result *= array[n - prev];
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int[] countOfNumber(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp;
    }
}