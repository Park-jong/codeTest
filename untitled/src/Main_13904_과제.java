import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_13904_과제 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Work[] works = new Work[n];
        int maxDeadline = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int deadline = Integer.parseInt(st.nextToken());
            int score = Integer.parseInt(st.nextToken());
            works[i] = new Work(deadline, score);
            maxDeadline = Math.max(maxDeadline, deadline);
        }
        int result = getMaxScore(works, maxDeadline);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getMaxScore(Work[] works, int deadline) {
        int n = works.length;
        Arrays.sort(works, (a, b) -> {
            if (a.deadline == b.deadline) {
                return Integer.compare(b.score, a.score);
            }
            return Integer.compare(a.deadline, b.deadline);
        });
        int[][] dp = new int[n + 1][deadline + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= deadline; j++) {
                Work work = works[i - 1];
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (work.deadline >= j) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + work.score);
                }
            }
        }
        return dp[n][deadline];
    }

    static class Work {
        int deadline;
        int score;

        Work(int deadline, int score) {
            this.deadline = deadline;
            this.score = score;
        }
    }
}