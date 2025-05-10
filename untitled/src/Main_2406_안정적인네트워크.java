import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_2406_안정적인네트워크 {
    static int[] set;
    static int[] setSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        makeSet(n);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            if (a != 0 && b != 0) union(a, b);
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        int[][] link = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                link[i][j] = Integer.parseInt(st.nextToken());
                if (i != 0 && j != 0 && i < j) {
                    pq.offer(new Edge(i, j, link[i][j]));
                }
            }
        }

        int cost = 0;
        int K = 0;
        if (n > 1 && setSize[findSet(1)] != n - 1) {
            while (!pq.isEmpty()) {
                Edge current = pq.poll();
                int a = current.num1;
                int b = current.num2;
                if (union(a, b)) {
                    cost += current.cost;
                    K++;
                    sb.append(a + 1).append(" ").append(b + 1).append("\n");
                }
            }
        }
        sb.insert(0, cost + " " + K + "\n");
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void makeSet(int n) {
        set = new int[n];
        setSize = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
            setSize[i] = 1;
        }
    }

    static int findSet(int num) {
        if (set[num] == num)
            return num;
        return set[num] = findSet(set[num]);
    }

    static boolean union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return false;
        set[rootB] = set[rootA];
        setSize[rootA] += set[rootB];
        return true;
    }

    static class Edge {
        int num1;
        int num2;
        int cost;

        Edge(int num1, int num2, int cost) {
            this.num1 = num1;
            this.num2 = num2;
            this.cost = cost;
        }
    }
}