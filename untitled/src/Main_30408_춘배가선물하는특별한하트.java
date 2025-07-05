import java.io.*;
import java.util.*;

public class Main_30408_춘배가선물하는특별한하트 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        String result = getResult(n, m);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String getResult(long n, long m) {
        return isPossible(n, m) ? "YES" : "NO";
    }

    static boolean isPossible(long n, long m) {
        Set<Long> set = new HashSet<>();
        set.add(n);
        Queue<Long> queue = new ArrayDeque<>();
        queue.offer(n);
        while (!queue.isEmpty()) {
            long cur = queue.poll();
            if (cur == m) {
                return true;
            }
            long n1 = cur >> 1;
            long n2 = (cur >> 1) + cur % 2;
            addSetAndQueue(set, queue, n1, m);
            addSetAndQueue(set, queue, n2, m);
        }
        return false;
    }

    static void addSetAndQueue(Set<Long> set, Queue<Long> queue, long num, long m) {
        if (set.contains(num) || num < m) return;
        set.add(num);
        queue.offer(num);
    }
}