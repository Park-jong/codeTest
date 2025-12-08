import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_26598_색종이와공예 {
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] paper = new char[n][m];
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                paper[i][j] = str.charAt(j);
            }
        }
        String result = isBeautiful(n, m, paper) ? "dd" : "BaboBabo";
        bw.write(result);
        bw.close();
        br.close();
    }

    static boolean isBeautiful(int n, int m, char[][] paper) {
        boolean[][] isVisited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (isVisited[i][j]) continue;
                if (!beautifulArea(n, m, paper, isVisited, i, j)) return false;
            }
        }
        return true;
    }

    static boolean beautifulArea(int n, int m, char[][] paper, boolean[][] isVisited, int x, int y) {
        Queue<Position> queue = new ArrayDeque<>();
        char c = paper[x][y];
        queue.offer(new Position(x, y));
        int count = 1;
        int maxX = x;
        int maxY = y;
        isVisited[x][y] = true;
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || paper[nx][ny] != c || isVisited[nx][ny]) continue;
                if (i > 1) return false;
                queue.offer(new Position(nx, ny));
                count++;
                maxX = Math.max(maxX, nx);
                maxY = Math.max(maxY, ny);
                isVisited[nx][ny] = true;
            }
        }
        return count == (maxX - x + 1) * (maxY - y + 1);
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