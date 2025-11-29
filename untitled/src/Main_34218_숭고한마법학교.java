import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_34218_숭고한마법학교 {
    static int n;
    static int m;
    static int[][] map;
    static int[][] set;
    static int[][] count;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        Position start = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
        st = new StringTokenizer(br.readLine());
        Position goal = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
        int result = findMinUsedMana(start, goal);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinUsedMana(Position start, Position goal) {
        makeSet();
        int startGroup = set[start.x][start.y];
        int goalGroup = set[goal.x][goal.y];
        Queue<Position> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (set[i][j] == startGroup) {
                    queue.offer(new Position(i, j));
                }
            }
        }
        int result = 0;
        main:
        while (!queue.isEmpty()) {
            Position position = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = position.x + dx[i];
                int ny = position.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || set[nx][ny] == startGroup || count[nx][ny] > 0) continue;
                if (set[nx][ny] == goalGroup) {
                    result = count[position.x][position.y] + 1;
                    break main;
                }
                queue.offer(new Position(nx, ny));
                count[nx][ny] = count[position.x][position.y] + 1;
            }
        }
        return result;
    }

    static void makeSet() {
        set = new int[n][m];
        count = new int[n][m];
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0 || set[i][j] > 0) continue;
                group(i, j, ++num);
            }
        }
    }

    static void group(int x, int y, int num) {
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(new Position(x, y));
        set[x][y] = num;
        while (!queue.isEmpty()) {
            Position position = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = position.x + dx[i];
                int ny = position.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || map[nx][ny] == 0 || set[nx][ny] > 0) continue;
                queue.offer(new Position(nx, ny));
                set[nx][ny] = num;
            }
        }
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