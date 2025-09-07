import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_7569_토마토 {

    static int n;
    static int m;
    static int h;
    static int[][][] tomato;
    static int[] dh = {0, 0, 0, 0, 1, -1};
    static int[] dx = {1, -1, 0, 0, 0, 0};
    static int[] dy = {0, 0, 1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        tomato = new int[h][n][m];
        Queue<int[]> queue = new ArrayDeque<>();
        int count = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < m; k++) {
                    tomato[i][j][k] = Integer.parseInt(st.nextToken());
                    if (tomato[i][j][k] == 1) {
                        queue.offer(new int[]{i, j, k, 0});
                    } else if (tomato[i][j][k] == 0) {
                        count++;
                    }
                }
            }
        }
        int result = getDay(queue, count);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getDay(Queue<int[]> queue, int count) {
        int compareCount = 0;
        int day = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            day = current[3];
            for (int i = 0; i < 6; i++) {
                int nh = current[0] + dh[i];
                int nx = current[1] + dx[i];
                int ny = current[2] + dy[i];
                if (nh < 0 || nx < 0 || ny < 0 || nh >= h || nx >= n || ny >= m || tomato[nh][nx][ny] != 0) {
                    continue;
                }
                queue.offer(new int[]{nh, nx, ny, day + 1});
                tomato[nh][nx][ny] = 1;
                compareCount++;
            }
        }
        if (compareCount != count) {
            return -1;
        }
        return day;
    }
}