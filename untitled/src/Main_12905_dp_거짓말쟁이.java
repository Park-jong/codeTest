import java.io.*;
import java.util.Arrays;

public class Main_12905_dp_거짓말쟁이 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        String str = br.readLine();
        char[] people = new char[n];
        for (int i = 0; i < n; i++) {
            people[i] = str.charAt(i);
        }
        int firstIsTrue = ifFirstTruth(people, n);
        int firstIsLie = ifFirstLiar(people, n);
        int result = result(firstIsTrue, firstIsLie);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int ifFirstTruth(char[] people, int n) {
        int[][] dp = makeDp(n);
        dp[0][0] = 0;
        fillDp(dp, people, n);
        return dp[n][0];
    }

    static int ifFirstLiar(char[] people, int n) {
        int[][] dp = makeDp(n);
        dp[0][1] = 0;
        fillDp(dp, people, n);
        return dp[n][1];
    }

    static int[][] makeDp(int n) {
        int[][] dp = new int[n + 1][2];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        return dp;
    }

    static void fillDp(int[][] dp, char[] people, int n) {
        for (int i = 0; i < n; i++) {
            if (dp[i][0] != Integer.MAX_VALUE) {
                if (people[i] == 'H') {
                    dp[i + 1][0] = Math.min(dp[i][0], dp[i + 1][0]);
                } else if (people[i] == 'L') {
                    dp[i + 1][1] = Math.min(dp[i][0] + 1, dp[i + 1][1]);
                } else {
                    dp[i + 1][0] = Math.min(dp[i][0], dp[i + 1][0]);
                    dp[i + 1][1] = Math.min(dp[i][0] + 1, dp[i + 1][1]);
                }
            }
            if (dp[i][1] != Integer.MAX_VALUE) {
                if (people[i] == 'H') {
                    dp[i + 1][1] = Math.min(dp[i][1] + 1, dp[i + 1][1]);
                } else if (people[i] == 'L') {
                    dp[i + 1][0] = Math.min(dp[i][1], dp[i + 1][0]);
                } else {
                    dp[i + 1][0] = Math.min(dp[i][1], dp[i + 1][0]);
                    dp[i + 1][1] = Math.min(dp[i][1] + 1, dp[i + 1][1]);
                }
            }
        }
    }

    static int result(int ifTrue, int ifLie){
        int result = Math.min(ifTrue, ifLie);
        if (result == Integer.MAX_VALUE) {
            result = -1;
        }
        return result;
    }
}