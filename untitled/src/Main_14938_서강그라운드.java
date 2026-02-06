import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_14938_서강그라운드 {
    static int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int[] items = new int[n];
        int[][] roads = new int[n][n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            items[i] = Integer.parseInt(st.nextToken());
            Arrays.fill(roads[i], MAX_VALUE);
            roads[i][i] = 0;
        }
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int distance = Integer.parseInt(st.nextToken());
            roads[a][b] = distance;
            roads[b][a] = distance;
        }
        int result = findMaxItem(n, m, items, roads);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxItem(int n, int m, int[] items, int[][] roads) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    roads[j][k] = Math.min(roads[j][k], roads[j][i] + roads[i][k]);
                }
            }
        }
        int maxItem = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (roads[i][j] <= m) {
                    count += items[j];
                }
            }
            maxItem = Math.max(maxItem, count);
        }
        return maxItem;
    }
}