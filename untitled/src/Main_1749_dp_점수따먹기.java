import java.io.*;
import java.util.StringTokenizer;

public class Main_1749_dp_점수따먹기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] matrix = new int[n][m];
        int[][] sum = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
                sum[i][j + 1] = sum[i][j] + matrix[i][j];
            }
        }
        int[][] dp = new int[m][m];
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = j; k < m; k++) {
                    int current = sum[i][k + 1] - sum[i][j];
                    dp[j][k] = Math.max(dp[j][k] + current, current);
                    result = Math.max(result, dp[j][k]);
                }
            }
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }
}