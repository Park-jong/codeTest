import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_27945_슬슬가지를먹지않으면죽는다 {
    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingInt(a -> a.t));
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            if (t > n) continue;
            edges.offer(new Edge(u, v, t));
        }
        int result = findDessert(n, edges);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findDessert(int n, PriorityQueue<Edge> edges) {
        makeSet(n);
        int result = 1;
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int u = edge.u;
            int v = edge.v;
            if (!union(u, v)) break;
            if (edge.t != result) break;
            result++;
        }
        return result;
    }

    static void makeSet(int n) {
        set = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            set[i] = i;
        }
    }

    static int findSet(int a) {
        if (set[a] == a) return a;
        return set[a] = findSet(set[a]);
    }

    static boolean union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return false;
        set[rootB] = set[rootA];
        return true;
    }

    static class Edge {
        int u;
        int v;
        int t;

        Edge(int u, int v, int t) {
            this.u = u;
            this.v = v;
            this.t = t;
        }
    }
}