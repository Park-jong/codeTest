import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main_19587_객실배치 {

    static final long DIVIDE = 1_000_000_007;
    static Map<Long, long[][]> arrayMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        arrayMap = new HashMap<>();
        long n = Long.parseLong(br.readLine());
        long result = getTotalCountOfNumber(n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long getTotalCountOfNumber(long num) {
        long[][] base = new long[3][3];
        for (int i = 0; i < 3; i++) {
            base[i][i] = 1;
        }
        arrayMap.put(1L, base);
        long[][] last = halfCountOfNumberMultiply(num);
        long sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum = (sum + last[i][j]) % DIVIDE;
            }
        }
        return sum;
    }

    static long[][] halfCountOfNumberMultiply(long num) {
        if (arrayMap.containsKey(num)) {
            return arrayMap.get(num);
        }
        long left = num >> 1;
        long right = (num >> 1) + num % 2;
        long[][] l = halfCountOfNumberMultiply(left);
        long[][] r = halfCountOfNumberMultiply(right);
        long[][] mul = multiply(l, r);
        arrayMap.put(num, mul);
        return mul;
    }

    static long[][] multiply(long[][] a, long[][] b) {
        long[][] c = new long[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                c[i][j] = (a[i][0] * b[1][j]) % DIVIDE
                        + (a[i][0] * b[2][j]) % DIVIDE
                        + (a[i][1] * b[0][j]) % DIVIDE
                        + (a[i][1] * b[2][j]) % DIVIDE
                        + (a[i][2] * b[0][j]) % DIVIDE
                        + (a[i][2] * b[1][j]) % DIVIDE
                        + (a[i][2] * b[2][j]) % DIVIDE;
                c[i][j] %= DIVIDE;
            }
        }
        return c;
    }
}