import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_27896_특별한서빙 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] x = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            x[i] = Integer.parseInt(st.nextToken());
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        int sum = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            pq.offer(x[i]);
            sum += x[i];
            if (sum >= m) {
                int give = pq.poll();
                sum -= (give << 1);
                count++;
            }
        }

        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }
}