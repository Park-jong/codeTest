import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1726_로봇 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        Position start = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, 0);
        st = new StringTokenizer(br.readLine());
        Position end = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, 0);
        int result = findMinCommend(map, start, end);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinCommend(int[][] map, Position start, Position end) {
        int n = map.length;
        int m = map[0].length;
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(start);
        boolean[][][] isVisited = new boolean[n][m][4];
        isVisited[start.x][start.y][start.d] = true;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        int result = 0;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 1; i < 4; i++) {
                int nx = current.x + dx[current.d] * i;
                int ny = current.y + dy[current.d] * i;
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || map[nx][ny] == 1) break;
                if (isVisited[nx][ny][current.d]) continue;
                if (nx == end.x && ny == end.y && current.d == end.d) return current.count + 1;
                queue.offer(new Position(nx, ny, current.d, current.count + 1));
                isVisited[nx][ny][current.d] = true;
            }
            int nd = current.d < 2 ? 2 : 0;
            if (!isVisited[current.x][current.y][nd]) {
                if (current.x == end.x && current.y == end.y && nd == end.d) {
                    result = current.count + 1;
                    break;
                }
                queue.offer(new Position(current.x, current.y, nd, current.count + 1));
                isVisited[current.x][current.y][nd] = true;
            }
            nd = current.d < 2 ? 3 : 1;
            if (!isVisited[current.x][current.y][nd]) {
                if (current.x == end.x && current.y == end.y && nd == end.d) {
                    result = current.count + 1;
                    break;
                }
                queue.offer(new Position(current.x, current.y, nd, current.count + 1));
                isVisited[current.x][current.y][nd] = true;
            }
        }
        return result;
    }

    static class Position {
        int x;
        int y;
        int d;
        int count;

        Position(int x, int y, int d, int count) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.count = count;
        }
    }
}