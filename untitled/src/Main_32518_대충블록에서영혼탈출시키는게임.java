import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main_32518_대충블록에서영혼탈출시키는게임 {
    static Map<Long, Long> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        long n = Long.parseLong(br.readLine());
        long result = countBlock(n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long countBlock(long n) {
        map.put(1L, 0L);
        map.put(2L, 0L);
        map.put(3L, 1L);
        map.put(4L, 1L);
        return divide(n);
    }

    static long divide(long x) {
        if (map.containsKey(x)) {
            return map.get(x);
        }
        long x_ = x - 2;
        long y = x_ / 3;
        long z = x_ / 3 + (x_ % 3 > 1 ? 1 : 0);
        long w = x_ / 3 + (x_ % 3 > 0 ? 1 : 0);
        long sum = divide(y) + divide(z) + divide(w) + 2;
        if (x_ % 3 == 2) {
            sum = Math.max(sum, divide(y) + divide(y) + divide(y + 2) + 2);
        }
        map.put(x, sum);
        return sum;
    }
}