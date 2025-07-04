import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_25189_시니컬한개구리 {

    static int n;
    static int m;
    static Position start;
    static Position home;
    static boolean[][][] isVisited;
    static boolean[] visitLineX;
    static boolean[] visitLineY;
    static int[][] map;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int result = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        start = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, 0, false);
        home = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, 0, false);
        isVisited = new boolean[2][n][m];
        visitLineX = new boolean[n];
        visitLineY = new boolean[m];
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findHome();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findHome() {
        if (startIsHome()) {
            return 0;
        }
        Queue<Position> queue = makeQueueAndAddStart();
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (notUseChance(current)) {
                useOneChaneStep(queue, current);
            }
            normalStep(queue, current);
            if (result != -1) {
                return result;
            }
        }
        return -1;
    }

    static boolean startIsHome() {
        return start.x == home.x && start.y == home.y;
    }

    static Queue<Position> makeQueueAndAddStart() {
        Queue<Position> queue = new ArrayDeque<>();
        visit(queue, start.x, start.y, start.step, start.chance);
        return queue;
    }

    static void visit(Queue<Position> queue, int x, int y, int step, boolean chance) {
        int useChance = chance ? 1 : 0;
        if (isVisited[useChance][x][y]) {
            return;
        }
        Position position = new Position(x, y, step, chance);
        queue.offer(position);
        isVisited[useChance][x][y] = true;
    }

    static boolean positionIsHome(int x, int y) {
        return x == home.x && y == home.y;
    }

    static boolean notUseChance(Position position) {
        return !position.chance;
    }

    static void useOneChaneStep(Queue<Position> queue, Position position) {
        int x = position.x;
        int y = position.y;
        int step = position.step + 1;
        if(!visitLineY[y]) {
            for (int i = 0; i < n; i++) {
                if (positionIsHome(i, y)) {
                    result = step;
                }
                visit(queue, i, y, step, true);
            }
            visitLineY[y] = true;
        }
        if(!visitLineX[x]){
            for (int i = 0; i < m; i++) {
                if (positionIsHome(x, i)) {
                    result = step;
                }
                visit(queue, x, i, step, true);
            }
            visitLineX[x] = true;
        }
    }

    static void normalStep(Queue<Position> queue, Position position) {
        int x = position.x;
        int y = position.y;
        int step = position.step + 1;
        for (int i = 0; i < 4; i++) {
            int newx = x + dx[i] * map[x][y];
            int newy = y + dy[i] * map[x][y];
            if (newx < 0 || newy < 0 || newx >= n || newy >= m) {
                continue;
            }
            if (positionIsHome(newx, newy)) {
                result = step;
            }
            visit(queue, newx, newy, step, position.chance);
        }
    }

    static class Position {
        int x;
        int y;
        int step;
        boolean chance;

        Position(int x, int y, int step, boolean chance) {
            this.x = x;
            this.y = y;
            this.step = step;
            this.chance = chance;
        }
    }
}