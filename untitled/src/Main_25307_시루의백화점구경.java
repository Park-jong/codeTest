import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_25307_시루의백화점구경 {

    static int n;
    static int m;
    static int k;
    static int[][] store;
    static boolean[][] isVisited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        store = new int[n][m];
        isVisited = new boolean[n][m];
        Queue<Position> manikin = new ArrayDeque<>();
        Position start = new Position(0, 0, 0);
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                store[i][j] = Integer.parseInt(st.nextToken());
                if (store[i][j] == 3) {
                    manikin.add(new Position(i, j, 0));
                    isVisited[i][j] = true;
                    store[i][j] = 1;
                } else if (store[i][j] == 4) {
                    start.x = i;
                    start.y = j;
                }
            }
        }
        fillManikinArea(manikin);
        int result = findChair(start);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void fillManikinArea(Queue<Position> manikin) {
        while (!manikin.isEmpty()) {
            Position position = manikin.poll();
            for (int i = 0; i < 4; i++) {
                int x = position.x + dx[i];
                int y = position.y + dy[i];
                int count = position.count + 1;
                if (isInStore(x, y) && count <= k && !isVisited[x][y]) {
                    if (store[x][y] != 4) {
                        store[x][y] = 1;
                        isVisited[x][y] = true;
                    }
                    manikin.offer(new Position(x, y, count));
                }
            }
        }
    }

    static boolean isInStore(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < m;
    }

    static int findChair(Position start) {
        Queue<Position> queue = new ArrayDeque<>();
        queue.offer(start);
        isVisited[start.x][start.y] = true;
        while (!queue.isEmpty()) {
            Position position = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x = position.x + dx[i];
                int y = position.y + dy[i];
                int count = position.count + 1;
                if (isInStore(x, y) && !isVisited[x][y] && store[x][y] != 1) {
                    if (store[x][y] == 2) {
                        return count;
                    }
                    queue.offer(new Position(x, y, count));
                    isVisited[x][y] = true;
                }
            }
        }
        return -1;
    }

    static class Position {
        int x;
        int y;
        int count;

        Position(int x, int y, int count) {
            this.x = x;
            this.y = y;
            this.count = count;
        }
    }
}