import java.io.*;
import java.util.StringTokenizer;

public class Main_10999_구간합구하기2 {

    static long[] arr;
    static long[] sum;
    static long[] change;
    static int n;
    static int m;
    static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        arr = new long[n];
        sum = new long[n * 4];
        change = new long[n * 4];
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }
        init(0, n - 1, 1);
        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            if (a == 1) {
                int b = Integer.parseInt(st.nextToken()) - 1;
                int c = Integer.parseInt(st.nextToken()) - 1;
                long d = Long.parseLong(st.nextToken());
                update(0, n - 1, 1, b, c, d);
            } else {
                int b = Integer.parseInt(st.nextToken()) - 1;
                int c = Integer.parseInt(st.nextToken()) - 1;
                sb.append(getSum(0, n - 1, 1, b, c)).append("\n");
            }
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static long init(int start, int end, int node) {
        if (start == end) return sum[node] = arr[start];
        int mid = (start + end) / 2;
        return sum[node] = init(start, mid, node * 2) + init(mid + 1, end, node * 2 + 1);
    }

    static void lazy(int start, int end, int node){
        if (change[node] != 0) {
            sum[node] += change[node] * (end - start + 1);
            if (end != start) {
                change[node * 2] += change[node];
                change[node * 2 + 1] += change[node];
            }
            change[node] = 0;
        }
    }

    static long getSum(int start, int end, int node, int left, int right) {
        lazy(start, end, node);
        if (end < left || start > right) return 0;
        if (start >= left && end <= right) return sum[node];
        int mid = (start + end) / 2;
        return getSum(start, mid, node * 2, left, right) + getSum(mid + 1, end, node * 2 + 1, left, right);
    }

    static void update(int start, int end, int node, int left, int right, long diff) {
        lazy(start, end, node);
        if (end < left || start > right) {
            return;
        }
        if (end <= right && start >= left) {
            sum[node] += diff * (end - start + 1);
            if (end != start) {
                change[node * 2] += diff;
                change[node * 2 + 1] += diff;
            }
            return;
        }
        int mid = (start + end) / 2;
        update(start, mid, node * 2, left, right, diff);
        update(mid + 1, end, node * 2 + 1, left, right, diff);
        sum[node] = sum[node * 2] + sum[node * 2 + 1];
    }
}