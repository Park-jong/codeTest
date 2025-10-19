import java.io.*;
import java.util.*;

public class Main_13910_개업 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] s = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMinOrder(n, m, s);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinOrder(int n, int m, int[] s) {
        int MAX_VALUE = Integer.MAX_VALUE >> 1;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < m; i++) {
            dp[s[i]] = 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                int sum = s[i] + s[j];
                if (sum <= n) {
                    dp[sum] = 1;
                }
            }
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.min(dp[i], dp[i - j] + dp[j]);
            }
        }
        return dp[n] == MAX_VALUE ? -1 : dp[n];
    }
}