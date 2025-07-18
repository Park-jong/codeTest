import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_20926_얼음미로 {

    static int width;
    static int height;
    static int[] dw = {1, -1, 0, 0};
    static int[] dh = {0, 0, 1, -1};
    static char[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());
        map = new char[height][width];
        Position start = new Position();
        for (int i = 0; i < height; i++) {
            String str = br.readLine();
            for (int j = 0; j < width; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == 'T') {
                    start.h = i;
                    start.w = j;
                }
            }
        }
        int result = getMinTime(start);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getMinTime(Position start) {
        PriorityQueue<Position> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        pq.offer(start);
        boolean[][] isVisited = new boolean[height][width];
        isVisited[start.h][start.w] = true;
        while (!pq.isEmpty()) {
            Position current = pq.poll();
            if (map[current.h][current.w] == 'E') {
                return current.time;
            }
            isVisited[current.h][current.w] = true;
            move4DirectionAndAddPq(pq, isVisited, current);
        }
        return -1;
    }

    static void move4DirectionAndAddPq(PriorityQueue<Position> pq, boolean[][] isVisited, Position current) {
        for (int i = 0; i < 4; i++) {
            Position nextPosition = findStopPositionOrNull(current, i);
            if(nextPosition != null && !isVisited[nextPosition.h][nextPosition.w]){
                pq.offer(nextPosition);
            }
        }
    }

    static Position findStopPositionOrNull(Position prev, int direction) {
        int w = prev.w;
        int h = prev.h;
        int time = prev.time;
        while (true) {
            int nextW = w + dw[direction];
            int nextH = h + dh[direction];
            if (isImpossibleRange(nextW, nextH)) {
                break;
            }
            char next = map[nextH][nextW];
            if (next >= '0' && next <= '9') {
                time += next - '0';
            } else if (next == 'E') {
                return new Position(nextW, nextH, time);
            } else if (next == 'R') {
                return new Position(w, h, time);
            } else if (next == 'H') {
                break;
            }
            w = nextW;
            h = nextH;
        }
        return null;
    }

    static boolean isImpossibleRange(int w, int h) {
        return w < 0 || h < 0 || w >= width || h >= height;
    }

    static class Position {
        int w;
        int h;
        int time;

        Position() {
        }

        Position(int w, int h, int time) {
            this.w = w;
            this.h = h;
            this.time = time;
        }
    }
}