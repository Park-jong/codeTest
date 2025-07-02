import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_22358_스키장 {

    static int n;
    static int m;
    static int k;
    static int start;
    static int goal;
    static Course[] courses;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        input(br);
        long result = findMaxTimeAtGoal();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void input(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());
        goal = Integer.parseInt(st.nextToken());
        courses = new Course[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long time = Long.parseLong(st.nextToken());
            courses[i] = new Course(a, b, time);
        }
        Arrays.sort(courses, Comparator.comparingInt(x -> x.a));
    }

    static long findMaxTimeAtGoal() {
        long[][] dp = initArray();
        for (int i = 0; i <= k; i++) {
            fillOneCycleMaxTime(dp[i]);
            takeLift(dp, i);
        }
        return findMaxValue(dp);
    }

    static long[][] initArray() {
        long[][] dp = new long[k + 1][n + 1];
        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][start] = 0;
        return dp;
    }

    static void fillOneCycleMaxTime(long[] dp) {
        for (int i = 0; i < m; i++) {
            Course course = courses[i];
            if (dp[course.a] < 0) {
                continue;
            }
            dp[course.b] = Math.max(dp[course.a] + course.time, dp[course.b]);
        }
    }

    static void takeLift(long[][] dp, int lift) {
        if (lift == k) {
            return;
        }
        for (int i = 0; i < m; i++) {
            Course course = courses[i];
            if (dp[lift][course.b] < 0) {
                continue;
            }
            dp[lift + 1][course.a] = Math.max(dp[lift][course.b], dp[lift + 1][course.a]);
        }
    }

    static long findMaxValue(long[][] dp) {
        long max = -1;
        for (int i = 0; i <= k; i++) {
            max = Math.max(dp[i][goal], max);
        }
        return max;
    }

    static class Course {
        int a;
        int b;
        long time;

        Course(int a, int b, long time) {
            this.a = a;
            this.b = b;
            this.time = time;
        }
    }
}