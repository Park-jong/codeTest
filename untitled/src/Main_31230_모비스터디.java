import java.io.*;
import java.util.*;

public class Main_31230_모비스터디 {

    static int n;
    static int m;
    static int start;
    static int goal;
    static Node[] nodes;
    static final long MAX_VALUE = Long.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());
        goal = Integer.parseInt(st.nextToken());
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            nodes[a].next.add(new Road(b, c));
            nodes[b].next.add(new Road(a, c));
        }
        String result = getCities();
        bw.write(result);
        bw.close();
        br.close();
    }

    static String getCities() {
        findShortDistance();
        Set<Integer> cities = new TreeSet<>();
        boolean[] visited = new boolean[n + 1];
        visited[goal] = true;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(goal);
        cities.add(goal);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int prev : nodes[current].prev) {
                if (!visited[prev]) {
                    visited[prev] = true;
                    queue.offer(prev);
                    cities.add(prev);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cities.size()).append("\n");
        for (int i : cities) {
            sb.append(i).append(" ");
        }
        return sb.toString();
    }

    static void findShortDistance() {
        long[] distance = new long[n + 1];
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(distance, MAX_VALUE);
        distance[start] = 0;
        PriorityQueue<Road> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.distance));
        pq.offer(new Road(start, 0));
        while (!pq.isEmpty()) {
            Road r = pq.poll();
            if (visited[r.index]) continue;
            visited[r.index] = true;
            if (r.index == goal) {
                break;
            }
            for (Road next : nodes[r.index].next) {
                if (visited[next.index]) continue;
                if (distance[next.index] > distance[r.index] + next.distance) {
                    distance[next.index] = distance[r.index] + next.distance;
                    pq.offer(new Road(next.index, distance[next.index]));
                    nodes[next.index].prev = new ArrayList<>();
                    nodes[next.index].prev.add(r.index);
                } else if (distance[next.index] == distance[r.index] + next.distance) {
                    nodes[next.index].prev.add(r.index);
                }
            }
        }
    }

    static class Node {
        int index;
        List<Road> next = new ArrayList<>();
        List<Integer> prev = new ArrayList<>();

        Node(int index) {
            this.index = index;
        }
    }

    static class Road {
        int index;
        long distance;

        Road(int index, long distance) {
            this.index = index;
            this.distance = distance;
        }
    }
}