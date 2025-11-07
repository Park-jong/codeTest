import java.io.*;
import java.util.StringTokenizer;

public class Main_17250_은하철도 {

    static int[] set;
    static int[] count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] planets = new int[n];
        for (int i = 0; i < n; i++) {
            planets[i] = Integer.parseInt(br.readLine());
        }
        Link[] links = new Link[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            links[i] = new Link(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        String result = countPlanets(n, planets, links);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String countPlanets(int n, int[] planets, Link[] links) {
        StringBuilder sb = new StringBuilder();
        makeSet(n, planets);
        for (Link link : links) {
            sb.append(union(link.a, link.b)).append("\n");
        }
        return sb.toString();
    }

    static void makeSet(int n, int[] planets) {
        set = new int[n + 1];
        count = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            set[i] = i;
            count[i] = planets[i - 1];
        }
    }

    static int findSet(int a) {
        if (set[a] == a) return a;
        return set[a] = findSet(set[a]);
    }

    static int union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return count[rootA];
        set[rootB] = rootA;
        return count[rootA] = count[rootA] + count[rootB];
    }

    static class Link {
        int a;
        int b;

        Link(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}