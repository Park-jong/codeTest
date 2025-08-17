import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_30686_과제제출하기 {

    static int min = Integer.MAX_VALUE;
    static int[] day;
    static boolean[] isSolved;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] knowledge = new int[n];
        int[][] problem = new int[m][];
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
        dfs(problem, knowledge, 0, 0);
        bw.write(String.valueOf(min));
        bw.close();
        br.close();
    }

    static void dfs(int[][] problem, int[] knowledge, int depth, int count) {
        if (depth == problem.length) {
            min = Math.min(min, count);
            return;
        }
        for (int i = 0; i < problem.length; i++) {
            if (isSolved[i]) continue;
            List<Integer> list = new ArrayList<>();
            List<Integer> before = new ArrayList<>();
            for (int require : problem[i]) {
                if (day[require] <= depth) {
                    list.add(require);
                    before.add(day[require]);
                    day[require] = depth + knowledge[require];
                }
            }
            int nextCount = count + list.size();
            isSolved[i] = true;
            dfs(problem, knowledge, depth + 1, nextCount);
            isSolved[i] = false;
            int size = list.size();
            for (int j = 0; j < size; j++) {
                int index = list.get(j);
                int b = before.get(j);
                day[index] = b;
            }
        }
    }
}