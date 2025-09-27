import java.io.*;
import java.util.StringTokenizer;

public class Main_31929_너재능있어 {

    static int n;
    static int m;
    static int[] win;
    static int[] lose;
    static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        win = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            win[i] = Integer.parseInt(st.nextToken());
        }
        m = Integer.parseInt(br.readLine());
        lose = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            lose[i] = Integer.parseInt(st.nextToken());
        }
        k = Integer.parseInt(br.readLine());
        int result = findMaxScore();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxScore() {
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = win(dp[i - 1][0], i);
        }
        for (int i = 1; i <= m; i++) {
            dp[0][i] = lose(dp[0][i - 1], i);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = Math.max(win(dp[i - 1][j], i), lose(dp[i][j - 1], j));
            }
        }
        return dp[n][m];
    }

    static int win(int score, int winCount) {
        return score + win[winCount - 1];
    }

    static int lose(int score, int loseCount) {
        int down;
        if (score >= 0) {
            down = Math.min(score % k, lose[loseCount - 1]);
            if (down == 0) down = lose[loseCount - 1];
        } else {
            down = Math.min(k + (score % k), lose[loseCount - 1]);
            if (down == 0) down = lose[loseCount - 1];
        }
        return score - down;
    }
}