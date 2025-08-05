import java.io.*;
import java.util.*;

public class Main_16681_등산 {

    static int n;
    static int m;
    static int d;
    static int e;
    static int[] height;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        height = new int[n];
        List<Road>[] uphill = new List[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            height[i] = Integer.parseInt(st.nextToken());
            uphill[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int point1 = Integer.parseInt(st.nextToken()) - 1;
            int point2 = Integer.parseInt(st.nextToken()) - 1;
            int dist = Integer.parseInt(st.nextToken());
            if (height[point1] > height[point2]) {
                uphill[point2].add(new Road(point2, point1, dist));
            } else if (height[point1] < height[point2]) {
                uphill[point1].add(new Road(point1, point2, dist));
            }
        }
        long result = getMaxValue(uphill);
        String resultString = result == Long.MIN_VALUE ? "Impossible" : String.valueOf(result);
        bw.write(resultString);
        bw.close();
        br.close();
    }

    static long getMaxValue(List<Road>[] uphill) {
        long[] upDistance = distances(uphill, 0);
        long[] downDistance = distances(uphill, n - 1);
        long result = Long.MIN_VALUE;
        for (int i = 1; i < n - 1; i++) {
            if (isPossibleRoad(upDistance[i], downDistance[i])){
                long value = (long)height[i] * e - (upDistance[i] + downDistance[i]) * d;
                result = Math.max(result, value);
            }
        }
        return result;
    }

    static long[] distances(List<Road>[] hill, int start) {
        PriorityQueue<Road> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.dist));
        for (Road road : hill[start]) {
            pq.offer(road);
        }
        long[] distance = new long[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[start] = 0;
        while (!pq.isEmpty()) {
            Road road = pq.poll();
            if (visited[road.goal]) {
                continue;
            }
            distance[road.goal] = road.dist;
            visited[road.goal] = true;
            for (Road next : hill[road.goal]) {
                if (visited[next.goal]) {
                    continue;
                }
                long nextDistance = road.dist + next.dist;
                pq.offer(new Road(start, next.goal, nextDistance));
            }
        }
        return distance;
    }

    static boolean isPossibleRoad(long distance1, long distance2) {
        return distance1 != Long.MAX_VALUE && distance2 != Long.MAX_VALUE;
    }

    static class Road {
        int start;
        int goal;
        long dist;

        Road(int start, int goal, long dist) {
            this.start = start;
            this.goal = goal;
            this.dist = dist;
        }
    }
}