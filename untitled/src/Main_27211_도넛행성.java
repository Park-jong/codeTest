import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_27211_도넛행성 {

    static int n;
    static int m;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = countArea(map);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int countArea(int[][] map) {
        int count = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0) {
                    count++;
                    fill(i, j, map, queue);
                }
            }
        }
        return count;
    }

    static void fill(int x, int y, int[][] map, Queue<int[]> queue) {
        queue.offer(new int[]{x, y});
        map[x][y] = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = (dx[i] + cur[0] + n) % n;
                int ny = (dy[i] + cur[1] + m) % m;
                if (map[nx][ny] == 0) {
                    queue.offer(new int[]{nx, ny});
                    map[nx][ny] = 1;
                }
            }
        }
    }
}