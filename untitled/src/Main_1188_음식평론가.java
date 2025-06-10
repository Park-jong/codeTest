import java.io.*;
import java.util.StringTokenizer;

public class Main_1188_음식평론가 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        n %= m;
        int result = cut(n, m);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int cut(int n, int m) {
        if (n == 0) return 0;
        int gcd = GCD(m, n);
        m /= gcd;
        int cutCount = m - 1;
        return cutCount * gcd;
    }

    static int GCD(int a, int b) {
        int remain = a % b;
        if (remain == 0) {
            return b;
        }
        return GCD(b, remain);
    }
}