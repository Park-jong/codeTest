import java.io.*;
import java.util.StringTokenizer;

public class Main_1423_원숭이키우기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] characters = new int[n];
        int[] power = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            characters[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            power[i] = Integer.parseInt(st.nextToken());
        }
        int d = Integer.parseInt(br.readLine());
        bw.write(String.valueOf(findMaxPower(n, d, characters, power)));
        bw.flush();
        bw.close();
        br.close();
    }

    static long findMaxPower(int n, int d, int[] characters, int[] power) {
        long[][] increase = new long[n][n];
        for (int i = 0; i < n; i++) {
            if (characters[i] == 0) continue;
            for (int j = i + 1; j < n; j++) {
                if (power[i] >= power[j]) continue;
                increase[i][j] = power[j] - power[i];
            }
        }
        long[] dp = new long[d + 1];
        for (int i = 0; i < n; i++) {
            int l = Math.min(characters[i], d);
            if (l == 0) continue;
            for (int j = 0; j < l; j++) {
                for (int range = d; range >= 0; range--) {//순서 중요
                    for (int k = 1; i + k < n; k++) {//순서 중요
                        if (increase[i][i + k] <= 0) continue;
                        if (range < k) break;
                        dp[range] = Math.max(dp[range - k] + increase[i][i + k], dp[range]);
                    }
                }
            }
        }
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += (long) characters[i] * (long) power[i];
        }
        long max = 0;
        for (int i = 0; i <= d; i++) {
            max = Math.max(max, dp[i]);
        }
        return result + max;
    }
}