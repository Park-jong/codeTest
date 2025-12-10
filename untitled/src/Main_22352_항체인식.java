import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_22352_항체인식 {
    static int n;
    static int m;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int[][] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int[][] before = new int[n][m];
        int[][] after = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                before[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                after[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        String result = isPossible(before, after) ? "YES" : "NO";
        bw.write(result);
        bw.close();
        br.close();
    }

    static boolean isPossible(int[][] before, int[][] after) {
        boolean[][] isVisited = new boolean[n][m];
        set = new int[n][m];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (isVisited[i][j]) continue;
                makeSet(i, j, isVisited, before, ++index);
            }
        }
        int beforeSet = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (before[i][j] == after[i][j]) continue;
                if (beforeSet == -1) {
                    if (!isNormal(i, j, before, after)) return false;
                    beforeSet = set[i][j];
                } else if (beforeSet != set[i][j]) return false;

            }
        }
        return true;
    }

    static void makeSet(int x, int y, boolean[][] isVisited, int[][] before, int index) {
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(new Position(x, y));
        isVisited[x][y] = true;
        set[x][y] = index;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || isVisited[nx][ny]
                        || before[nx][ny] != before[current.x][current.y])
                    continue;
                queue.offer(new Position(nx, ny));
                isVisited[nx][ny] = true;
                set[nx][ny] = index;
            }
        }
    }

    static boolean isNormal(int x, int y, int[][] before, int[][] after) {
        boolean[][] isVisited = new boolean[n][m];
        int index = after[x][y];
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(new Position(x, y));
        isVisited[x][y] = true;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || isVisited[nx][ny]
                        || before[nx][ny] != before[current.x][current.y])
                    continue;
                if (after[nx][ny] != index) return false;
                queue.offer(new Position(nx, ny));
                isVisited[nx][ny] = true;
            }
        }
        return true;
    }

    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}