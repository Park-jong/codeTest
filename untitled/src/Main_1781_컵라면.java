import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_1781_컵라면 {

    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Problem[] problems = new Problem[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            problems[i] = new Problem(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int result = findMaxCup(problems);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxCup(Problem[] problems) {
        Arrays.sort(problems, Comparator.comparingInt(a -> -a.cup));
        makeSet(problems.length);
        int result = 0;
        for (Problem p : problems) {
            if (useEmpty(p.deadLine)) {
                result += p.cup;
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

    static boolean useEmpty(int a) {
        int rootA = findSet(a);
        if (rootA == 0) return false;
        set[rootA] = findSet(rootA - 1);
        return true;
    }

    static class Problem {
        int deadLine;
        int cup;

        Problem(int deadLine, int cup) {
            this.deadLine = deadLine;
            this.cup = cup;
        }
    }
}