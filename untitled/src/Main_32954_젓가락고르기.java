import java.io.*;
import java.util.StringTokenizer;

public class Main_32954_젓가락고르기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        Long[] a = new Long[n];
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(st.nextToken());
        }
        long result = findMinX(k, a);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findMinX(long k, Long[] a) {
        if (k == 0) return 0;
        if (isImpossible(k, a)) {
            return -1;
        }
        long badCase = a.length;
        long count = 0;
        for (long num : a) {
            if (num % 2 == 0) {
                num = num - 1;
            }
            if (num > 2) {
                long c = num / 2;
                if (count + c < k) {
                    badCase += num - 1;
                    count += c;
                } else {
                    badCase += (k - count) * 2 - 1;
                    count = k;
                    break;
                }
            }
        }
        if (count < k) {
            badCase += k - count;
        }
        return badCase;
    }

    static boolean isImpossible(long k, Long[] a) {
        long sum = 0;
        for (long i : a) {
            sum += i / 2;
        }
        return sum < k;
    }
}