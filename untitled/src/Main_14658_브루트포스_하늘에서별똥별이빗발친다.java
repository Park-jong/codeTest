import java.io.*;
import java.util.*;

public class Main_14658_브루트포스_하늘에서별똥별이빗발친다 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Point[] point = new Point[K];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            point[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(point, (a, b) -> Integer.compare(a.x, b.x));

        int maxSize = 0;
        for (int i = 0; i < K; i++) {
            PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.y, b.y));
            int startX = point[i].x;
            int endX = startX + L;
            for (int j = i; j < K; j++) {
                if (point[j].x > endX) break;
                pq.offer(point[j]);
            }
            Queue<Point> q = new ArrayDeque<>();
            q.offer(pq.poll());
            maxSize = Math.max(maxSize, q.size());
            while (!pq.isEmpty()) {
                Point current = pq.poll();
                while (!q.isEmpty() && q.peek().y + L < current.y) {
                    q.poll();
                }
                q.offer(current);
                maxSize = Math.max(maxSize, q.size());

            }
        }
        bw.write(String.valueOf(K - maxSize));
        bw.flush();
        bw.close();
        br.close();
    }
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

