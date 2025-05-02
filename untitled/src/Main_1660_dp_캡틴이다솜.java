import java.io.*;
import java.util.Arrays;

public class Main_1660_dp_캡틴이다솜 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        int MAX_VALUE = Integer.MAX_VALUE >> 1;
        Arrays.fill(dp, MAX_VALUE);
        dp[0] = 0;
        for (int i = 1, triangle = 1, tower = 1; tower <= n; i++, triangle += i, tower += triangle) {
            for (int j = tower; j <= n; j++) {
                dp[j] = Math.min(dp[j], dp[j - tower] + 1);
            }
        }
        bw.write(String.valueOf(dp[n]));
        bw.flush();
        bw.close();
        br.close();
    }
}