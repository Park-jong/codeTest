import java.io.*;
import java.util.*;

public class Main_6988_타일밟기 {

    static boolean[] isExist = new boolean[1000001];
    static int[] index = new int[1000001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
            isExist[array[i]] = true;
            index[array[i]] = i;
        }
        long result = getMaxSum(array, n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long getMaxSum(int[] array, int n) {
        long max = 0;
        long[][] dp = new long[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int cur = array[i];
                int prev = array[j];
                int sub = cur - prev;
                int twoStep = prev - sub;
                if(twoStep < 1 || !isExist[twoStep]){
                    dp[j][i] = cur + prev;
                }else{
                    int twoPrev = index[twoStep];
                    dp[j][i] = dp[twoPrev][j] + cur;
                    max = Math.max(max, dp[j][i]);
                }
            }
        }
        return max;
    }
}