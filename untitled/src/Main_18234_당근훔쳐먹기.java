import java.io.*;
import java.util.StringTokenizer;

public class Main_18234_당근훔쳐먹기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        long result = 0;
        int[] p = new int[101];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            result += Integer.parseInt(st.nextToken());
            p[Integer.parseInt(st.nextToken())]++;
        }
        for (int i = 1, j = 0; i <= 100; i++) {
            if (p[i] <= 0) continue;
            result += (long) (t - n + j) * i * p[i] + (((long) (p[i] - 1) * p[i]) >> 1) * i;
            j += p[i];
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}