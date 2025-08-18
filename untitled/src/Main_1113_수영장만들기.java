import java.io.*;
import java.util.*;

public class Main_1113_수영장만들기 {
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static int n;
    static int m;
    static int[][] area;
    static boolean[][] isImpossible;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        area = new int[n][m];
        isImpossible = new boolean[n][m];
        int max = 0;
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                area[i][j] = str.charAt(j) - '0';
                max = Math.max(max, area[i][j]);
            }
        }
        int result = checkAllArea();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int checkAllArea() {
        int sum = 0;
        Queue<Position> queue = new ArrayDeque<>();
        List<Integer> visited = new ArrayList<>();
        boolean[][] isVisited = new boolean[n][m];
        for (int height = 1; height < 10; height++) {
            for (int i = 1; i < n - 1; i++) {
                for (int j = 1; j < m - 1; j++) {
                    if (area[i][j] >= height || isImpossible[i][j]) continue;
                    List<Integer> list = checkCurrentArea(queue, visited, isVisited, i, j, height);
                    for (int index : list) {
                        int x = index / m;
                        int y = index % m;
                        sum += height - area[x][y];
                        area[x][y] = height;
                    }
                }
            }
        }
        return sum;
    }

    static List<Integer> checkCurrentArea(Queue<Position> queue, List<Integer> visited, boolean[][] isVisited, int x, int y, int height) {
        for (int i = 0; i < n; i++) {
            Arrays.fill(isVisited[i], false);
        }
        isVisited[x][y] = true;
        Position start = new Position(x, y);
        queue.clear();
        queue.offer(start);
        visited.clear();
        visited.add(x * m + y);
        while (!queue.isEmpty()) {
            Position position = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = position.x + dx[i];
                int ny = position.y + dy[i];
                if (isVisited[nx][ny]) continue;
                if (area[nx][ny] < height) {
                    if (nx == 0 || nx == n - 1 || ny == 0 || ny == m - 1 || isImpossible[nx][ny]) {
                        isImpossible[x][y] = true;
                        return Collections.emptyList();
                    }
                    Position newPosition = new Position(nx, ny);
                    queue.offer(newPosition);
                    visited.add(nx * m + ny);
                    isVisited[nx][ny] = true;
                }
            }
        }
        return visited;
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