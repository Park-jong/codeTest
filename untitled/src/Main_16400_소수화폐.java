import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main_16400_소수화폐 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        List<Integer> primeList = getPrimes(n);
        int result = numberOfCount(primeList, n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static List<Integer> getPrimes(int n) {
        boolean[] isNotPrime = new boolean[n + 1];
        List<Integer> primeList = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isNotPrime[i]) {
                continue;
            }
            primeList.add(i);
            for (int j = i; j <= n; j += i) {
                isNotPrime[j] = true;
            }
        }
        return primeList;
    }

    static int numberOfCount(List<Integer> primeList, int n) {
        final int divide = 123456789;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int prime : primeList) {
            for (int j = prime; j <= n; j++) {
                dp[j] += dp[j - prime];
                dp[j] %= divide;
            }
        }
        return dp[n];
    }
}