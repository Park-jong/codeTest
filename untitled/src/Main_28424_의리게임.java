import java.io.*;
import java.util.StringTokenizer;

public class Main_28424_의리게임 {

    static int n;
    static int[] m;
    static int[] drink;
    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        m = new int[n];
        drink = new int[n];
        for (int i = 0; i < n; i++) {
            m[i] = Integer.parseInt(br.readLine());
        }
        makeSet();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            if (type == 1) {
                int index = Integer.parseInt(st.nextToken()) - 1;
                int x = Integer.parseInt(st.nextToken());
                drink(index, x);
            } else {
                int index = Integer.parseInt(st.nextToken()) - 1;
                sb.append(print(index)).append("\n");
            }
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void makeSet() {
        set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
    }

    static int findSet(int i) {
        if (set[i] == i) return i;
        return set[i] = findSet(set[i]);
    }

    static void union(int a, int b) {
        if (b >= n) return;
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return;
        set[rootA] = set[rootB];
    }

    static void drink(int i, int x) {
        int j = i;
        while (j < n) {
            j = findSet(j);
            int possible = m[j] - drink[j];
            if (x < possible) {
                drink[j] += x;
                break;
            } else {
                drink[j] = m[j];
                x -= possible;
                union(j, j + 1);
            }
            j++;
        }
    }

    static int print(int i) {
        return drink[i];
    }
}