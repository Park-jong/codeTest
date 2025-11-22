import java.io.*;
import java.util.StringTokenizer;

public class Main_23257_비트코인은신이고나는무적이다 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] values = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            values[i] = Math.abs(Integer.parseInt(st.nextToken()));
        }
        int result = findMaxValue(n, m, values);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxValue(int n, int m, int[] values) {
        int max = 1 << 10;
        boolean[][] dp = new boolean[2][max];
        for (int i = 0; i < n; i++) {
            dp[1][values[i]] = true;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < max; j++) {
                if (dp[i % 2][j]) {
                    for (int k = 0; k < n; k++)
                        dp[(i + 1) % 2][j ^ values[k]] = true;
                }
            }
        }
        int result = 0;
        for (int i = max - 1; i > 0; i--) {
            if (dp[m % 2][i]) {
                result = Math.max(i, result);
                break;
            }
        }
        return result;
    }
}