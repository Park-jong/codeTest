import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_5427_불 {

    static int[] dh = {1, -1, 0, 0};
    static int[] dw = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            int result = solve(br);
            sb.append(result >= 0 ? result : "IMPOSSIBLE").append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int solve(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        char[][] map = new char[h][w];
        Position position = new Position(0, 0, 0);
        for (int i = 0; i < h; i++) {
            String str = br.readLine();
            for (int j = 0; j < w; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == '@') {
                    position.h = i;
                    position.w = j;
                }
            }
        }
        return findMinExit(w, h, map, position);
    }

    static int findMinExit(int w, int h, char[][] map, Position position) {
        Queue<Position> queue = new ArrayDeque<>();
        Queue<Position> fire = new ArrayDeque<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (map[i][j] == '*') {
                    fire.offer(new Position(i, j, 0));
                }
            }
        }
        queue.offer(position);
        boolean[][] isVisited = new boolean[h][w];
        isVisited[position.h][position.w] = true;
        int level = -1;
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            if (p.level > level) {
                level++;
                burn(map, fire, level);
            }
            for (int i = 0; i < 4; i++) {
                int nw = dw[i] + p.w;
                int nh = dh[i] + p.h;
                if (nw < 0 || nh < 0 || nw >= w || nh >= h) {
                    return level + 1;
                }
                if (isVisited[nh][nw] || map[nh][nw] == '*' || map[nh][nw] == '#') continue;
                queue.offer(new Position(nh, nw, level + 1));
                isVisited[nh][nw] = true;
            }
        }
        return -1;
    }

    static void burn(char[][] map, Queue<Position> fire, int level) {
        int h = map.length;
        int w = map[0].length;
        while (!fire.isEmpty() && fire.peek().level == level) {
            Position f = fire.poll();
            for (int i = 0; i < 4; i++) {
                int nw = dw[i] + f.w;
                int nh = dh[i] + f.h;
                if (nw < 0 || nh < 0 || nw >= w || nh >= h || map[nh][nw] == '*' || map[nh][nw] == '#') continue;
                map[nh][nw] = '*';
                fire.offer(new Position(nh, nw, level + 1));
            }
        }
    }

    static class Position {
        int h;
        int w;
        int level;

        Position(int h, int w, int level) {
            this.h = h;
            this.w = w;
            this.level = level;
        }
    }
}