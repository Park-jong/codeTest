import java.io.*;
import java.util.StringTokenizer;

public class Main_1332_풀자 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        int[] p = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            p[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMin(n, v, p);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMin(int n, int v, int[] p) {
        int result = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(p[i] - p[j]) < v) continue;
                result = Math.min(result, (i / 2 + i % 2) + ((j - i) / 2 + (j - i) % 2) + 1);
            }
        }
        return result;
    }
}