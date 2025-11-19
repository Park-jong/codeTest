import java.io.*;
import java.util.*;

public class Main_12763_지각하면안돼 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        StringTokenizer st = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(br.readLine());
        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int time = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            nodes[a].next.add(new Road(b, time, cost));
            nodes[b].next.add(new Road(a, time, cost));
        }
        int result = findMinCost(nodes, t, m);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinCost(Node[] nodes, int t, int m) {
        int n = nodes.length;
        int[] minTimeToGoal = new int[n];
        Arrays.fill(minTimeToGoal, Integer.MAX_VALUE);
        minTimeToGoal[n - 1] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{n - 1, 0});
        boolean[] isVisited = new boolean[n];
        int count = 0;
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int index = current[0];
            int time = current[1];
            if (isVisited[index]) continue;
            isVisited[index] = true;
            minTimeToGoal[index] = time;
            count++;
            if (count == n) break;
            for (Road r : nodes[index].next) {
                if (isVisited[r.next] || minTimeToGoal[r.next] <= r.time + time) continue;
                pq.offer(new int[]{r.next, r.time + time});
            }
        }
        if (minTimeToGoal[0] > t) return -1;
        pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.offer(new int[]{0, 0, 0});
        int result = -1;
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int index = current[0];
            int time = current[1];
            int cost = current[2];
            if (index == n - 1) {
                result = cost;
                break;
            }
            if (time >= nodes[index].time) continue;
            nodes[index].time = time;
            for (Road r : nodes[index].next) {
                int nextTime = time + r.time;
                if (nextTime >= nodes[r.next].time || nextTime + minTimeToGoal[r.next] > t || cost + r.cost > m)
                    continue;
                pq.offer(new int[]{r.next, nextTime, cost + r.cost});
            }
        }
        return result;
    }

    static class Node {
        int time = Integer.MAX_VALUE;
        List<Road> next = new ArrayList<>();
    }

    static class Road {
        int next;
        int time;
        int cost;

        Road(int next, int time, int cost) {
            this.next = next;
            this.time = time;
            this.cost = cost;
        }
    }
}