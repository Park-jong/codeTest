import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_27114_dp_조교의맹연습 {

    static final int MAX_VALUE = Integer.MAX_VALUE / 2;
    static int left;
    static int right;
    static int back;
    static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        left = Integer.parseInt(st.nextToken());
        right = Integer.parseInt(st.nextToken());
        back = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        int result = turn();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int turn() {
        int[][] dp = new int[k + 1][4];
        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < 4; j++) {
                if(dp[i][j] == MAX_VALUE) continue;
                turnLeft(i, j, dp);
                turnRight(i, j, dp);
                turnBack(i, j, dp);
            }
        }
        return dp[k][0] != MAX_VALUE ? dp[k][0] : -1;
    }

    static void turnLeft(int energy, int direction, int[][] dp) {
        int newEnergy = energy + left;
        int newDirection = turnLeftDirection(direction);
        if (newEnergy > k) return;
        dp[newEnergy][newDirection] = Math.min(dp[energy][direction] + 1, dp[newEnergy][newDirection]);
    }

    static int turnLeftDirection(int direction) {
        return (direction + 3) % 4;
    }

    static void turnRight(int energy, int direction, int[][] dp) {
        int newEnergy = energy + right;
        int newDirection = turnRightDirection(direction);
        if (newEnergy > k) return;
        dp[newEnergy][newDirection] = Math.min(dp[energy][direction] + 1, dp[newEnergy][newDirection]);
    }

    static int turnRightDirection(int direction) {
        return (direction + 1) % 4;
    }

    static void turnBack(int energy, int direction, int[][] dp) {
        int newEnergy = energy + back;
        int newDirection = turnBackDirection(direction);
        if (newEnergy > k) return;
        dp[newEnergy][newDirection] = Math.min(dp[energy][direction] + 1, dp[newEnergy][newDirection]);
    }

    static int turnBackDirection(int direction) {
        return (direction + 2) % 4;
    }
}