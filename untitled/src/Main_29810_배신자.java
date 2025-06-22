import java.io.*;
import java.util.StringTokenizer;

public class Main_29810_배신자 {

    static int[] set;
    static int[] size;
    static int[] includeBet;
    static int bet;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        makeSet(n);
        Link[] links = new Link[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            links[i] = new Link(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
        }
        bet = Integer.parseInt(br.readLine()) - 1;
        for (int i = 0; i < m; i++) {
            union(links[i].a, links[i].b);
        }
        int result = findMaxGroup(n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void makeSet(int n) {
        set = new int[n];
        size = new int[n];
        includeBet = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
            size[i] = 1;
        }
    }

    static void union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (a == bet) {
            includeBet[rootB]++;
            return;
        }
        if (b == bet) {
            includeBet[rootA]++;
            return;
        }
        if (rootA == rootB) {
            return;
        }
        set[rootB] = rootA;
        size[rootA] = size[rootA] + size[rootB];
        includeBet[rootA] = includeBet[rootA] + includeBet[rootB];
    }

    static class Link {
        int a;
        int b;

        Link(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static int findSet(int n) {
        if (set[n] == n) {
            return n;
        }
        return set[n] = findSet(set[n]);
    }

    static int findMaxGroup(int n){
        int max = 0;
        boolean[] isVisited = new boolean[n];
        for (int i = 0; i < n; i++) {
            int root = findSet(i);
            if (isVisited[root]) {
                continue;
            }
            int setSize = size[root];
            if(includeBet[root] == 1){
                setSize++;
            }
            max = Math.max(max, setSize);
            isVisited[root] = true;
        }
        return max;
    }
}