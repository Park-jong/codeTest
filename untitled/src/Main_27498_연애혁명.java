import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_27498_연애혁명 {

    static int[] set;
    static Love[] loves;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        makeSet(n);
        loves = new Love[m];
        int totalLoveAmount = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            loves[i] = new Love(a, b, c, d);
            totalLoveAmount += c;
        }
        Arrays.sort(loves, (a, b) -> {
            if (a.love == b.love) {
                return Integer.compare(b.amount, a.amount);
            }
            return Integer.compare(b.love, a.love);
        });
        int result = findMinRemoveLove(totalLoveAmount);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void makeSet(int n) {
        set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
    }

    static int findMinRemoveLove(int totalLoveAmount) {
        int sum = 0;
        for (Love love : loves) {
            int student1 = love.p1;
            int student2 = love.p2;
            if (union(student1, student2)) {
                sum += love.amount;
            }
        }
        return totalLoveAmount - sum;
    }

    static boolean union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) {
            return false;
        }
        set[rootB] = rootA;
        return true;
    }

    static int findSet(int n) {
        if (set[n] == n) {
            return n;
        }
        return set[n] = findSet(set[n]);
    }

    static class Love {
        int p1;
        int p2;
        int amount;
        int love;

        Love(int a, int b, int c, int d) {
            p1 = a;
            p2 = b;
            amount = c;
            love = d;
        }
    }
}