import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main_1029_그림교환 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                cost[i][j] = str.charAt(j) - '0' + 1;
            }
        }
        int result = findMaxOwner(n, cost);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxOwner(int n, int[][] cost) {
        int bit = 1 << n;
        int[][] dp = new int[n][bit];
        Set<Integer>[] sets = new Set[n];
        for (int i = 0; i < n; i++) {
            sets[i] = new HashSet<>();
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][1] = 1;
        sets[0].add(1);
        int result = 1;
        for (int loop = 1; loop < n; loop++) {
            Set<Integer> bitSet = sets[loop - 1];
            for (int i = 1; i < n; i++) {
                for (int b : bitSet) {
                    if ((b & (1 << i)) != 0) continue;
                    for (int j = 0; j < n; j++) {
                        if (dp[j][b] <= cost[j][i]) {
                            dp[i][b | (1 << i)] = Math.min(dp[i][b | (1 << i)], cost[j][i]);
                            sets[loop].add(b | (1 << i));
                            result = loop + 1;
                        }
                    }
                }
            }
        }
        return result;
    }
}