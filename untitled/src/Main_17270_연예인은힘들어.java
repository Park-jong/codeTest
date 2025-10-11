import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_17270_연예인은힘들어 {

    static final int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[v + 1][v + 1];
        for (int i = 1; i <= v; i++) {
            Arrays.fill(map[i], MAX_VALUE);
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            map[a][b] = Math.min(c, map[a][b]);
            map[b][a] = Math.min(c, map[b][a]);
        }
        st = new StringTokenizer(br.readLine());
        int J = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int result = findMinPosition(v, map, J, S);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinPosition(int v, int[][] map, int J, int S) {
        int result = -1;
        for (int i = 1; i <= v; i++) {
            for (int j = 1; j <= v; j++) {
                if (i == j) continue;
                for (int k = 1; k <= v; k++) {
                    if (i == k || j == k) continue;
                    if (map[j][k] > map[j][i] + map[i][k]) {
                        map[j][k] = map[j][i] + map[i][k];
                    }
                }
            }
        }
        int distance = MAX_VALUE;
        int sum = MAX_VALUE;
        for (int i = 1; i <= v; i++) {
            if (J == i || S == i || sum < map[J][i] + map[i][S]) continue;
            if (sum > map[J][i] + map[i][S]) {
                sum = map[J][i] + map[i][S];
                distance = MAX_VALUE;
                result = -1;
            }
            if (map[J][i] > map[i][S]) continue;
            if (distance > map[J][i]) {
                distance = map[J][i];
                result = i;
            }
        }
        return result;
    }
}