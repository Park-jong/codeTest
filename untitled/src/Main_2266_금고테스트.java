import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2266_금고테스트 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int count = Integer.parseInt(st.nextToken());
        int result = findMinCount(n, count);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinCount(int n, int count) {
        int[][] dp = new int[count][n + 1];
        fillArray(dp, n, count);
        return dp[count - 1][n];
    }

    static void fillArray(int[][] dp, int n, int count){
        for (int i = 0; i <= n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < count; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][0] = 0;
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= j; k++) {
                    int fallCount = divideCount(dp, i, j, k);
                    dp[i][j] = Math.min(dp[i][j], fallCount);
                }
            }
        }
    }

    static int divideCount(int[][] dp, int remain, int highest, int center){
        int left = dp[remain - 1][center - 1];
        int right = dp[remain][highest - center];
        return Math.max(left, right) + 1;
    }
}