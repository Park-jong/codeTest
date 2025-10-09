import java.io.*;
import java.util.StringTokenizer;

public class Main_20002_사과나무 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findMaxValue(n, map);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxValue(int n, int[][] map) {
        int[][] sum = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] + map[i - 1][j - 1] - sum[i - 1][j - 1];
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int min = Math.min(i, j);
                for (int k = 1; k <= min; k++) {
                    max = Math.max(max, rangeSum(sum, i, j, k));
                }
            }
        }
        return max;
    }

    static int rangeSum(int[][] sum, int i, int j, int n) {
        return sum[i][j] - sum[i - n][j] - sum[i][j - n] + sum[i - n][j - n];
    }
}