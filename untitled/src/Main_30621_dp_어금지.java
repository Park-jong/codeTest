import java.io.*;
import java.util.StringTokenizer;

public class Main_30621_dp_어금지 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] time = setInput(br, n);
        int[] b = setInput(br, n);
        int[] c = setInput(br, n);
        long result = dp(n, time, b, c);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int[] setInput(BufferedReader br, int n) throws IOException {
        int[] array = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        return array;
    }

    static long dp(int n, int[] time, int[] b, int[] c) {
        long[] dp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            int timeIndex = binarySearch(time, time[i] - b[i]);
            dp[i] = Math.max(dp[i - 1], dp[timeIndex] + c[i]);
        }
        return dp[n];
    }

    static int binarySearch(int[] time, int n) {
        int left = 0;
        int right = time.length - 1;
        int result = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (time[mid] < n) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}