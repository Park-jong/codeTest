import java.io.*;
import java.util.*;

public class Main_20160_야쿠르트아줌마야쿠르트주세요 {

    static final long MAX_VALUE = Long.MAX_VALUE >> 1;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[v + 1];
        for (int i = 1; i <= v; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long weight = Integer.parseInt(st.nextToken());
            nodes[a].edges.add(new Edge(b, weight));
            nodes[b].edges.add(new Edge(a, weight));
        }
        st = new StringTokenizer(br.readLine());
        int[] point = new int[10];
        for (int i = 0; i < 10; i++) {
            point[i] = Integer.parseInt(st.nextToken());
        }
        int start = Integer.parseInt(br.readLine());
        long[] path = findPath(v, start, nodes);
        long time = 0;
        int yakult = point[0];
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long[] yPath = findPath(v, yakult, nodes);
            if (yPath[point[i]] == MAX_VALUE) {
                continue;
            }
            time += yPath[point[i]];
            yakult = point[i];
            if (time >= path[point[i]]) {
                result = Math.min(point[i], result);
            }
        }
        if (result == Integer.MAX_VALUE) {
            result = -1;
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long[] findPath(int v, int start, Node[] nodes) {
        long[] result = new long[v + 1];
        Arrays.fill(result, MAX_VALUE);
        result[start] = 0;
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a.weight));
        queue.offer(new Path(start, 0));
        while (!queue.isEmpty()) {
            Path current = queue.poll();
            if (result[current.next] < current.weight) {
                continue;
            }
            for (Edge edge : nodes[current.next].edges) {
                if (result[edge.next] > result[current.next] + edge.weight) {
                    result[edge.next] = result[current.next] + edge.weight;
                    queue.offer(new Path(edge.next, result[edge.next]));
                }
            }
        }
        return result;
    }

    static class Node {
        int index;
        List<Edge> edges = new ArrayList<>();

        Node(int index) {
            this.index = index;
        }
    }

    static class Edge {
        int next;
        long weight;

        Edge(int next, long weight) {
            this.next = next;
            this.weight = weight;
        }
    }

    static class Path {
        int next;
        long weight;

        Path(int next, long weight) {
            this.next = next;
            this.weight = weight;
        }
    }
}