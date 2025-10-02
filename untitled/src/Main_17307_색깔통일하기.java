import java.io.*;
import java.util.StringTokenizer;

public class Main_17307_색깔통일하기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int[] x = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            x[i] = Integer.parseInt(st.nextToken());
        }
        Result result = findMinPress(n, c, x);
        String str = result.index + "\n" + result.count;
        bw.write(str);
        bw.close();
        br.close();
    }

    static Result findMinPress(int n, int c, int[] x) {
        long[] leftToRight = new long[n];
        long[] rightToLeft = new long[n];
        for (int i = 1; i < n; i++) {
            leftToRight[i] = leftToRight[i - 1] + LTR(x[i - 1], x[i], c);
            rightToLeft[n - i - 1] = rightToLeft[n - i] + RTL(x[n - i - 1], x[n - i], c);
        }
        int index = 0;
        long result = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            long right = leftToRight[n - 1] - leftToRight[i];
            long left = rightToLeft[0] - rightToLeft[i];
            long count = Math.max(right, left);
            if (result > count) {
                index = i + 1;
                result = count;
            }
        }
        return new Result(index, result);
    }

    static long LTR(long left, long right, int c) {
        return right >= left ? right - left : right + c - left;
    }

    static long RTL(long left, long right, int c) {
        return left >= right ? left - right : left + c - right;
    }

    static class Result {
        int index;
        long count;

        Result(int index, long count) {
            this.index = index;
            this.count = count;
        }
    }
}