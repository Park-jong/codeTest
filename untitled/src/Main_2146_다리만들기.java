import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2146_다리만들기 {
    static int[][] set;
    static int[][] map;
    static int n;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findMinBridge();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinBridge() {
        makeSet();
        Queue<Position> queue = addEdgeSide();
        int[][] move = new int[n][n];
        int result = Integer.MAX_VALUE;
        int step = 0;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (step != move[current.x][current.y] && result != Integer.MAX_VALUE) {
                break;
            }
            step = move[current.x][current.y];
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + current.x;
                int ny = dy[i] + current.y;
                if (nx < 0 || ny < 0 || nx >= n || ny >= n || map[nx][ny] == 1 || set[current.x][current.y] == set[nx][ny])
                    continue;
                if (set[nx][ny] != 0) {
                    result = Math.min(result, move[current.x][current.y] + move[nx][ny]);
                    continue;
                }
                queue.offer(new Position(nx, ny));
                set[nx][ny] = set[current.x][current.y];
                move[nx][ny] = move[current.x][current.y] + 1;
            }
        }
        return result;
    }

    static void makeSet() {
        set = new int[n][n];
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 1 && set[i][j] == 0) {
                    fillSet(i, j, ++num);
                }
            }
        }
    }

    static void fillSet(int x, int y, int num) {
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(new Position(x, y));
        set[x][y] = num;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + current.x;
                int ny = dy[i] + current.y;
                if (nx < 0 || ny < 0 || nx >= n || ny >= n || map[nx][ny] == 0 || set[nx][ny] != 0) continue;
                queue.offer(new Position(nx, ny));
                set[nx][ny] = num;
            }
        }
    }

    static Queue<Position> addEdgeSide() {
        Queue<Position> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 0) continue;
                queue.offer(new Position(i, j));
            }
        }
        return queue;
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