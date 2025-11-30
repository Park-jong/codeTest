import java.io.*;
import java.util.StringTokenizer;

public class Main_29704_벼락치기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        Problem[] problems = new Problem[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            problems[i] = new Problem(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int result = findMinPenalty(n, t, problems);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinPenalty(int n, int t, Problem[] problems) {
        int[][] dp = new int[n + 1][t + 1];
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            Problem problem = problems[i - 1];
            sum += problem.penalty;
            for (int j = 1; j <= t; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                if (j >= problem.day) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - problem.day] + problem.penalty);
                }
            }
        }
        return sum - dp[n][t];
    }

    static class Problem {
        int day;
        int penalty;

        Problem(int day, int penalty) {
            this.day = day;
            this.penalty = penalty;
        }
    }
}