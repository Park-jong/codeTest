import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_14220_dp_양아치집배원 {

    static final int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[][] distance = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findMinValue(distance, n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinValue(int[][] distance, int n){
        int[][] dp = makeDpArray(n);
        fillDpArray(dp, distance, n);
        return findMinValueInLastArray(dp, n);
    }

    static int[][] makeDpArray(int n){
        int[][] dp = new int[n][n];
        for (int i = 1; i < n; i++) {
            Arrays.fill(dp[i], MAX_VALUE);
        }
        return dp;
    }

    static void fillDpArray(int[][] dp, int[][] distance, int n){
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (distance[j][k] == 0) continue;
                    dp[i][j] = Math.min(dp[i][j], distance[j][k] + dp[i - 1][k]);
                }
            }
        }
    }

    static int findMinValueInLastArray(int[][] dp, int n){
        int result = MAX_VALUE;
        for(int i = 0; i < n; i++){
            result = Math.min(result, dp[n - 1][i]);
        }
        return result == MAX_VALUE ? -1 : result;
    }
}