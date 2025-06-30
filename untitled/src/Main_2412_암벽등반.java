import java.io.*;
import java.util.*;

public class Main_2412_암벽등반 {

    static Map<Integer, Map<Integer, Point>> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
            if (!map.containsKey(y)) {
                map.put(y, new HashMap<>());
            }
            map.get(y).put(x, points[i]);
        }
        int result = moveToGoal(t);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int moveToGoal(int goal) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(new Point(0, 0));
        return searchGoal(queue, goal);
    }

    static int searchGoal(Queue<Point> queue, int goal) {
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            for (int i = -2; i <= 2; i++) {
                int y = current.y + i;
                Map<Integer, Point> yMap = map.get(y);
                if (yMap == null) {
                    continue;
                }
                for (int j = -2; j <= 2; j++) {
                    int x = current.x + j;
                    Point point = yMap.get(x);
                    if (point == null || point.isVisited) {
                        continue;
                    }
                    checkPoint(point, current.move + 1);
                    if (point.y == goal) {
                        return point.move;
                    }
                    queue.offer(point);
                }
            }
        }
        return -1;
    }

    static void checkPoint(Point point, int move) {
        point.isVisited = true;
        point.move = move;
    }

    static class Point {
        int x;
        int y;
        boolean isVisited;
        int move;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}