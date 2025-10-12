import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1922_네트워크연결 {

    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        Line[] lines = new Line[m];
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            lines[i] = new Line(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int result = minCost(n, m, lines);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int minCost(int n, int m, Line[] lines) {
        makeSet(n);
        PriorityQueue<Line> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.c));
        for (int i = 0; i < m; i++) {
            pq.offer(lines[i]);
        }
        int count = 1;
        int result = 0;
        while (!pq.isEmpty() || count < n) {
            Line line = pq.poll();
            if (union(line.a, line.b)) {
                result += line.c;
                count++;
            }
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
        set[rootB] = rootA;
        return true;
    }

    static class Line {
        int a;
        int b;
        int c;

        Line(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}