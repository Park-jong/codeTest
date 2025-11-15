import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17222_최대유량_위스키거래 {
    static int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] flow = new int[n + m + 2][n + m + 2];
        int[] receive = new int[n + m + 2];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            receive[i] = Integer.parseInt(st.nextToken());
            flow[i][n + m + 1] = MAX_VALUE;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = n + 1; i <= n + m; i++) {
            receive[i] = Integer.parseInt(st.nextToken());
            flow[0][i] = receive[i];
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            for (int j = 0; j < x; j++) {
                int num = Integer.parseInt(st.nextToken());
                flow[n + 1 + i][num] = receive[num];
            }
        }
        int result = findMaxFlow(flow);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxFlow(int[][] flow) {
        int nm = flow.length;
        int result = 0;
        while (true) {
            int[] prev = findPath(flow);
            if (prev[nm - 1] == -1) break;
            int min = MAX_VALUE;
            for (int i = nm - 1; i != 0; i = prev[i]) {
                min = Math.min(min, flow[prev[i]][i]);
            }
            result += min;
            for (int i = nm - 1; i != 0; i = prev[i]) {
                flow[prev[i]][i] -= min;
                flow[i][prev[i]] += min;
            }
        }
        return result;
    }

    static int[] findPath(int[][] flow) {
        int nm = flow.length;
        int[] prev = new int[nm];
        Arrays.fill(prev, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int i = 1; i < nm; i++) {
                if (flow[current][i] <= 0 || prev[i] != -1) continue;
                prev[i] = current;
                if (i == nm - 1) return prev;
                queue.offer(i);
            }
        }
        return prev;
    }
}