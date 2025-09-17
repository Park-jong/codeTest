import java.io.*;
import java.util.StringTokenizer;

public class Main_1341_사이좋은형제 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        String result = solve(a, b);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String solve(long a, long b) {
        if (a == b) return "*";
        if (a == 0) return "-";
        StringBuilder sb = new StringBuilder();
        long total = 1;
        long eatTotal = 0;
        long eat = 0;
        for (long i = 0; i < 61; i++) {
            if (i == 60) return "-1";
            total *= 2;
            eatTotal = eatTotal * 2 + 1;
            eat *= 2;
            if (isSame(a, b, eat + 1, eatTotal)) {
                sb.append("*");
                break;
            } else if (isSame(a, b, eat, eatTotal)) {
                sb.append("-");
                break;
            }
            if (isBiggerThanAB(a, b, eat + 1, total)) {
                sb.append("-");
            } else {
                sb.append("*");
                eat++;
            }
        }
        return sb.toString();
    }

    static boolean isSame(long a, long b, long c, long d) {
        return a * d == c * b;
    }

    static boolean isBiggerThanAB(long a, long b, long c, long d) {
        return a * d < c * b;
    }
}