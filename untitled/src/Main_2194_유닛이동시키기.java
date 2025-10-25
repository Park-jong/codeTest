import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2194_유닛이동시키기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        boolean[][] wall = new boolean[n + 1][m + 1];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            for (int j = 0; j < a; j++) {
                if (x - j <= 0) continue;
                for (int j2 = 0; j2 < b; j2++) {
                    if (y - j2 <= 0) continue;
                    wall[x - j][y - j2] = true;
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        Position start = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0);
        st = new StringTokenizer(br.readLine());
        Position goal = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0);
        int result = findPath(n, m, a, b, wall, start, goal);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findPath(int n, int m, int a, int b, boolean[][] wall, Position start, Position goal) {
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(start);
        boolean[][] isVisited = new boolean[n + 1][m + 1];
        isVisited[start.x][start.y] = true;
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + current.x;
                int ny = dy[i] + current.y;
                if (nx <= 0 || ny <= 0 || nx > n - a + 1 || ny > m - b + 1 || wall[nx][ny] || isVisited[nx][ny])
                    continue;
                if (nx == goal.x && ny == goal.y) return current.time + 1;
                queue.offer(new Position(nx, ny, current.time + 1));
                isVisited[nx][ny] = true;
            }
        }
        return -1;
    }

    static class Position {
        int x;
        int y;
        int time;

        Position(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }
}