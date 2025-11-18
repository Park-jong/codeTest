import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_34409_무등산등반 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        int top = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                top = Math.max(top, map[i][j]);
            }
        }
        int result = findMinTime(n, m, x, y, a, b, c, map, top);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinTime(int n, int m, int x, int y, int a, int b, int c, int[][] map, int top) {
        PriorityQueue<Position> pq = new PriorityQueue<>((p1, p2) -> {
            if (p1.time == p2.time) return Integer.compare(p2.h, p1.h);
            return Integer.compare(p1.time, p2.time);
        });
        boolean[][] isVisited = new boolean[n][m];
        pq.offer(new Position(x, y, map[x][y], 0));
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        while (!pq.isEmpty()) {
            Position current = pq.poll();
            if (isVisited[current.x][current.y]) continue;
            isVisited[current.x][current.y] = true;
            if (map[current.x][current.y] == top) return current.time;
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + current.x;
                int ny = dy[i] + current.y;
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || isVisited[nx][ny]) continue;
                int diff = map[nx][ny] - map[current.x][current.y];
                if (Math.abs(diff) > c) continue;
                pq.offer(new Position(nx, ny, map[nx][ny], current.time + (diff > 0 ? a * diff : diff < 0 ? b * -diff : 1)));
            }
        }
        return -1;
    }

    static class Position {
        int x;
        int y;
        int h;
        int time;

        Position(int x, int y, int h, int time) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.time = time;
        }
    }
}