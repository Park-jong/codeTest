import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_1578_세계정복 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        Integer[] people = new Integer[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            people[i] = Integer.parseInt(st.nextToken());
        }
        long result = findMaxGroup(n, k, people);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findMaxGroup(int n, int k, Integer[] people) {
        Arrays.sort(people, Comparator.reverseOrder());
        long left = 0;
        long right = 0;
        for (int p : people) {
            right += p;
        }
        long result = right;
        while (left <= right) {
            long mid = (left + right) >> 1;
            long sum = 0;
            for (int i = 0; i < k; i++) {
                sum += Math.min(people[i], mid);
            }
            long remain = 0;
            for (int i = k; i < n; i++) {
                remain += people[i];
            }
            if (sum + remain >= mid * k) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}