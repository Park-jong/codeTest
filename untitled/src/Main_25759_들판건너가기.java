import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_25759_들판건너가기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] flowers = new int[n];
        for (int i = 0; i < n; i++) {
            flowers[i] = Integer.parseInt(st.nextToken());
        }
        int[] dp = new int[101];
        Arrays.fill(dp, -1);
        dp[flowers[0]] = 0;
        for (int i = 1; i < n; i++) {
            int max = 0;
            for (int j = 1; j <= 100; j++) {
                if (dp[j] < 0) continue;
                int sub = j - flowers[i];
                max = Math.max(dp[j] + sub * sub, max);
            }
            dp[flowers[i]] = Math.max(max, dp[flowers[i]]);
        }
        bw.write(String.valueOf(dp[flowers[n - 1]]));
        bw.flush();
        bw.close();
        br.close();
    }
}