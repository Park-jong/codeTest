import java.io.*;
import java.util.StringTokenizer;

public class Main_30686_과제제출하기 {

    static int min = Integer.MAX_VALUE;
    static int[] day;
    static boolean[] isSolved;
    static int n;
    static int m;
    static int[] knowledge;
    static int[][] problem;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        knowledge = new int[n];
        problem = new int[m][];
        day = new int[n];
        isSolved = new boolean[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            knowledge[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            problem[i] = new int[Integer.parseInt(st.nextToken())];
            for (int j = 0; j < problem[i].length; j++) {
                problem[i][j] = Integer.parseInt(st.nextToken()) - 1;
            }
        }
        order(0, 0);
        bw.write(String.valueOf(min));
        bw.close();
        br.close();
    }

    static void order(int depth, int count) {
        if (count >= min) return;
        if (depth == m) {
            min = count;
            return;
        }
        for (int i = 0; i < m; i++) {
            if (isSolved[i]) continue;
            int[] beforeIndex = new int[problem[i].length];
            int[] beforeValue = new int[problem[i].length];
            int size = 0;
            for (int require : problem[i]) {
                if (day[require] <= depth) {
                    beforeIndex[size] = require;
                    beforeValue[size++] = day[require];
                    day[require] = depth + knowledge[require];
                }
            }
            int nextCount = count + size;
            isSolved[i] = true;
            order(depth + 1, nextCount);
            isSolved[i] = false;
            for (int j = 0; j < size; j++) {
                int index = beforeIndex[j];
                int b = beforeValue[j];
                day[index] = b;
            }
        }
    }
}