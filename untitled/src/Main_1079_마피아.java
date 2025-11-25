import java.io.*;
import java.util.StringTokenizer;

public class Main_1079_마피아 {
    static int maxDays;
    static int n;
    static int[] point;
    static int[][] r;
    static int player;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        point = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            point[i] = Integer.parseInt(st.nextToken());
        }
        r = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                r[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        player = Integer.parseInt(br.readLine());
        findMaxNight();
        bw.write(String.valueOf(maxDays));
        bw.close();
        br.close();
    }

    static void findMaxNight() {
        boolean[] isDied = new boolean[n];
        if (n % 2 == 0) {
            playNight(isDied, n, 1);
        } else {
            int died = playDay(isDied);
            if (died != player) {
                isDied[died] = true;
                playNight(isDied, n - 1, 1);
            }
        }
    }

    static void playNight(boolean[] isDied, int remain, int count) {
        if (remain == 2) {
            maxDays = count;
            return;
        }
        for (int i = 0; i < n; i++) {
            if (isDied[i] || i == player) continue;
            isDied[i] = true;
            for (int j = 0; j < n; j++) {
                point[j] += r[i][j];
            }
            int day = playDay(isDied);
            if (day == player) {
                maxDays = Math.max(maxDays, count);
            } else {
                isDied[day] = true;
                playNight(isDied, remain - 2, count + 1);
                isDied[day] = false;
            }
            isDied[i] = false;
            for (int j = 0; j < n; j++) {
                point[j] -= r[i][j];
            }
        }
    }

    static int playDay(boolean[] isDied) {
        int n = point.length;
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < n; i++) {
            if (isDied[i]) continue;
            if (max < point[i]) {
                max = point[i];
                index = i;
            }
        }
        return index;
    }
}