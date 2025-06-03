import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_23317_dp_구슬굴리기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        int[][] dp = new int[n][n];
        dp[0][0] = 1;
        Position[] positions = new Position[m + 1];
        positions[0] = new Position(0, 0);
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            positions[i + 1] = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        fillAll(positions, dp, n, m);
        int result = addAll(dp[n - 1]);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void fillAll(Position[] positions, int[][] dp, int n, int m){
        Arrays.sort(positions, (a, b) -> Integer.compare(a.row, b.row));
        for (int i = 1; i <= m; i++) {
            int start = positions[i - 1].row;
            int end = positions[i].row;
            fillDP(dp, start, end);
            removeNotGoal(dp, positions[i]);
        }
        fillDP(dp, positions[m].row, n - 1);
    }

    static void fillDP(int[][] dp, int start, int end) {
        for (int j = start; j < end; j++) {
            for (int k = 0; k <= j; k++) {
                dp[j + 1][k] += dp[j][k];
                dp[j + 1][k + 1] += dp[j][k];
            }
        }
    }

    static void removeNotGoal(int[][] dp, Position position) {
        int row = position.row;
        int col = position.col;
        for (int j = 0; j <= row; j++) {
            if (j == col) continue;
            dp[row][j] = 0;
        }
    }

    static int addAll(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    static class Position {
        int row;
        int col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}