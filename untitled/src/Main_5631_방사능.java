import java.io.*;
import java.util.StringTokenizer;

public class Main_5631_방사능 {
    static int[] set;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        makeSet();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            union(a, b);
        }
        int result = setCount();
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

    static void union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return;
        set[rootB] = rootA;
    }

    static int findSet(int a) {
        if (set[a] == a)
            return a;
        return set[a] = findSet(set[a]);
    }

    static int setCount() {
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[findSet(i)]) {
                visited[findSet(i)] = true;
                count++;
            }
        }
        return count;
    }
}