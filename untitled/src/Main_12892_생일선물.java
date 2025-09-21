import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_12892_생일선물 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long d = Integer.parseInt(st.nextToken());
        long[][] present = new long[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            present[i][0] = Integer.parseInt(st.nextToken());
            present[i][1] = Integer.parseInt(st.nextToken());
        }
        long result = getMaxHappy(n, d, present);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long getMaxHappy(long n, long d, long[][] present) {
        Arrays.sort(present, Comparator.comparingLong(a -> a[0]));
        int i = 0;
        int j = 0;
        long sum = 0;
        long max = 0;
        while(i < n){
            while(j < n && present[j][0] - present[i][0] < d){
                sum += present[j][1];
                j++;
            }
            max = Math.max(max, sum);
            sum -= present[i++][1];
        }
        return max;
    }
}