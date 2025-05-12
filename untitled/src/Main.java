import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine()) - 1;

        int[] fib = new int[n];
        int[] dp = new int[n];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i < n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + fib[i];
        }

        int result = 0;
        //case 자유석 공석
        result = fib[k] * fib[n - k - 1];

        //case 자유석 공석 아님
        for (int i = 0; i < n; i++) {
            if (i == k) continue;
            int r = 0;
            if (i < k) {
                int a = dp[i] * fib[k - i - 1];
                int b = fib[i] * dp[k - i - 1];
                int c = fib[i] * fib[k - i - 1];
                r += ((a + b - c) * fib[n - k - 1]);
            } else {
                int a = dp[i - k - 1] * fib[n - i - 1];
                int b = fib[i - k - 1] * dp[n - i - 1];
                int c = fib[i - k - 1] * fib[n - i - 1];
                r += ((a + b - c) * fib[k]);
            }
            result += r;
        }

        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}