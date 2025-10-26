import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_23743_방탈출 {

    static int[] set;
    static int[] minExit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Warp[] warps = new Warp[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            warps[i] = new Warp(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int[] exit = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            exit[i] = Integer.parseInt(st.nextToken());
        }
        long result = findMinTime(n, m, warps, exit);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findMinTime(int n, int m, Warp[] warps, int[] exit) {
        Arrays.sort(warps, Comparator.comparingInt(a -> a.c));
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += exit[i];
        }
        makeSet(n, exit);
        for (int i = 0; i < m; i++) {
            if (isGroup(warps[i].a, warps[i].b)) continue;
            sum += unionIfLessThanExit(warps[i].a, warps[i].b, warps[i].c);
        }
        return sum;
    }

    static void makeSet(int n, int[] exit) {
        set = new int[n + 1];
        minExit = exit;
        for (int i = 1; i <= n; i++) {
            set[i] = i;
        }
    }

    static int findSet(int a) {
        if (a == set[a]) return a;
        return set[a] = findSet(set[a]);
    }

    static boolean isGroup(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        return rootB == rootA;
    }

    static int unionIfLessThanExit(int a, int b, int c) {
        int ea = minExit[findSet(a)];
        int eb = minExit[findSet(b)];
        if (ea + eb >= Math.min(ea, eb) + c) {
            union(a, b);
            return Math.min(ea, eb) + c - (ea + eb);
        }
        return 0;
    }

    static void union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        set[rootB] = set[rootA];
        minExit[rootB] = minExit[rootA] = Math.min(minExit[rootB], minExit[rootA]);
    }

    static class Warp {
        int a;
        int b;
        int c;

        Warp(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}