import java.io.*;
import java.util.StringTokenizer;

public class Main_31443_준영이 {
    static int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int result = findMaxHappiness(n, m);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxHappiness(int n, int m) {
        int max = Math.max(n, m);
        int min = Math.min(n, m);
        if (min == 0) return 1;
        if (max == 1) return 1;
        if (max == 2) return max * min;
        if (min == 1) {
            if (max % 3 == 0) {
                int c = max / 3;
                long result = 1;
                for (int i = 0; i < c; i++) {
                    result = (result * 3) % MOD;
                }
                return (int) result;
            } else if (max % 3 == 1) {
                int c = max / 3;
                long result = 1;
                for (int i = 0; i < c - 1; i++) {
                    result = (result * 3) % MOD;
                }
                return (int) ((((result * 2) % MOD) * 2) % MOD);
            } else {
                int c = max / 3;
                long result = 1;
                for (int i = 0; i < c; i++) {
                    result = (result * 3) % MOD;
                }
                return (int) ((result * 2) % MOD);
            }
        }
        int c = max / 3;
        long repeat = 1;
        for (int i = 0; i < c; i++) {
            repeat = (repeat * 3) % MOD;
        }
        long result = 1;
        for (int i = 0; i < min; i++) {
            result = (result * repeat) % MOD;
        }
        result = (result * findMaxHappiness(max % 3, min)) % MOD;
        return (int) result;
    }
}