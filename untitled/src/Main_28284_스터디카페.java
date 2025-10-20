import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_28284_스터디카페 {

    static long min;
    static long max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] seat = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            seat[i] = Integer.parseInt(st.nextToken());
        }
        User[] users = new User[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            users[i] = new User(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        findMinMaxCost(n, m, seat, users);
        bw.write(min + " " + max);
        bw.close();
        br.close();
    }

    static void findMinMaxCost(int n, int m, int[] seat, User[] users) {
        Arrays.sort(users, Comparator.comparingInt((a -> a.start)));
        Arrays.sort(seat);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long[] sum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = seat[i - 1] + sum[i - 1];
        }
        int day = 0;
        for (User user : users) {
            while (!pq.isEmpty() && pq.peek() < user.start) {
                int size = pq.size();
                int current = pq.poll();
                long minSum = sum[size];
                long maxSum = sum[n] - sum[n - size];
                min += (current - day + 1) * minSum;
                max += (current - day + 1) * maxSum;
                day = current + 1;
            }
            int size = pq.size();
            long minSum = sum[size];
            long maxSum = sum[n] - sum[n - size];
            min += (user.start - day) * minSum;
            max += (user.start - day) * maxSum;
            pq.offer(user.end);
            day = user.start;
        }
        while (!pq.isEmpty()) {
            int size = pq.size();
            int current = pq.poll();
            long minSum = sum[size];
            long maxSum = sum[n] - sum[n - size];
            min += (current - day + 1) * minSum;
            max += (current - day + 1) * maxSum;
            day = current + 1;
        }
    }

    static class User {
        int start;
        int end;

        User(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}