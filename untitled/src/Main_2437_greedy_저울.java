import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2437_greedy_저울 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] num = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }
        int sum = 1;
        Arrays.sort(num);
        for (int i = 0; i < n; i++) {
            if (sum >= num[i]) sum += num[i];
            else break;
        }
        bw.write(String.valueOf(sum));
        bw.close();
        br.close();
    }
}