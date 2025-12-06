import java.io.*;
import java.util.StringTokenizer;

public class Main_12979_종이접기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int result = findMinFold(w, h, a);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinFold(int w, int h, int a) {
        int sqrt = (int) Math.sqrt(a);
        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= sqrt; i++) {
            if (a % i == 0) {
                result = Math.min(result, fold(w, h, i, a / i));
                result = Math.min(result, fold(w, h, a / i, i));
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    static int fold(int w, int h, int x, int y) {
        if (w < x || h < y) return Integer.MAX_VALUE;
        int count = 0;
        while (w > x) {
            x = x << 1;
            count++;
        }
        while (h > y) {
            y = y << 1;
            count++;
        }
        return count;
    }
}