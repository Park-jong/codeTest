import java.io.*;
import java.util.StringTokenizer;

public class Main_2096_내려가기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][3];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            map[i][0] = Integer.parseInt(st.nextToken());
            map[i][1] = Integer.parseInt(st.nextToken());
            map[i][2] = Integer.parseInt(st.nextToken());
        }
        int[] score = getScore(n, map);
        String result = String.format("%d %d", score[0], score[1]);
        bw.write(result);
        bw.close();
        br.close();
    }

    static int[] getScore(int n, int[][] map) {
        int[][] maxDp = new int[n][3];
        int[][] minDp = new int[n][3];
        maxDp[0][0] = map[0][0];
        maxDp[0][1] = map[0][1];
        maxDp[0][2] = map[0][2];
        minDp[0][0] = map[0][0];
        minDp[0][1] = map[0][1];
        minDp[0][2] = map[0][2];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                int left = Math.max(0, j - 1);
                int right = Math.min(2, j + 1);
                int max = 0;
                int min = Integer.MAX_VALUE;
                for (int k = left; k <= right; k++) {
                    max = Math.max(max, maxDp[i - 1][k]);
                    min = Math.min(min, minDp[i - 1][k]);
                }
                maxDp[i][j] = max + map[i][j];
                minDp[i][j] = min + map[i][j];
            }
        }
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            max = Math.max(max, maxDp[n - 1][i]);
            min = Math.min(min, minDp[n - 1][i]);
        }
        return new int[]{max, min};
    }
}