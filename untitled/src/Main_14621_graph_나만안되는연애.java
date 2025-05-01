import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_14621_graph_나만안되는연애 {
    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        boolean[] isMan = new boolean[n];
        for (int i = 0; i < n; i++) {
            String gender = st.nextToken();
            if (gender.equals("M")) isMan[i] = true;
        }

        set = makeSet(n);
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.distance, b.distance));

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken()) - 1;
            int destination = Integer.parseInt(st.nextToken()) - 1;
            int distance = Integer.parseInt(st.nextToken());
            if (isMan[src] != isMan[destination]) pq.offer(new Edge(src, destination, distance));
        }

        int linkCount = 0;
        int result = 0;
        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (union(current.point1, current.point2)) {
                linkCount++;
                result += current.distance;
            }
            if (linkCount == n - 1) break;
        }

        bw.write(String.valueOf(linkCount == n - 1 ? result : -1));
        bw.flush();
        bw.close();
        br.close();
    }

    static int[] makeSet(int n) {
        int[] set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
        return set;
    }

    static int findSet(int index) {
        if (set[index] == index) return index;
        return set[index] = findSet(set[index]);
    }

    static boolean union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootA == rootB) return false;
        set[rootB] = rootA;
        return true;
    }

    static class Edge {
        int point1;
        int point2;
        int distance;

        Edge(int point1, int point2, int distance) {
            this.point1 = point1;
            this.point2 = point2;
            this.distance = distance;
        }
    }
}