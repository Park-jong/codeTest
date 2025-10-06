import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_14950_정복자 {

    static int[] set;
    static int n;
    static int m;
    static int t;
    static Road[] roads;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        roads = new Road[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int city1 = Integer.parseInt(st.nextToken()) - 1;
            int city2 = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());
            roads[i] = new Road(city1, city2, cost);
        }
        makeSet();
        int result = findMinCost();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void makeSet() {
        set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
    }

    static int findSet(int x) {
        if (x == set[x]) return x;
        return set[x] = findSet(set[x]);
    }

    static boolean union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return false;
        set[rootB] = set[rootA];
        return true;
    }

    static int findMinCost() {
        Arrays.sort(roads, Comparator.comparingInt(a -> a.cost));
        boolean[] isVisited = new boolean[n];
        int result = 0;
        int count = 0;
        for (int i = 0; i < m; i++) {
            int c1 = roads[i].city1;
            int c2 = roads[i].city2;
            int cost = roads[i].cost;
            if (!union(c1, c2)) continue;
            if (!isVisited[c1]) {
                count++;
                isVisited[c1] = true;
            }
            if (!isVisited[c2]) {
                count++;
                isVisited[c2] = true;
            }
            result += cost;
            if (count == n) break;
        }
        result += ((n - 1) * t * (n - 2) / 2);
        return result;
    }

    static class Road {
        int city1;
        int city2;
        int cost;

        Road(int city1, int city2, int cost) {
            this.city1 = city1;
            this.city2 = city2;
            this.cost = cost;
        }
    }
}