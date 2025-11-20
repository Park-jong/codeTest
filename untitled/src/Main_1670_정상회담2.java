import java.io.*;

public class Main_1670_정상회담2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int result = findNumberOfCases(n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findNumberOfCases(int n) {
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[2] = 1;
        for (int i = 4; i <= n; i += 2) {
            for (int j = i - 2; j >= 0; j -= 2) {
                dp[i] = (dp[i] + dp[j] * dp[i - 2 - j]) % 987_654_321;
            }
        }
        return (int) dp[n];
    }
}