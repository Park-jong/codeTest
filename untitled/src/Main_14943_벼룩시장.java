import java.io.*;
import java.util.StringTokenizer;

public class Main_14943_벼룩시장 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] count = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            count[i] = Integer.parseInt(st.nextToken());
        }
        long result = findCost(n, count);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findCost(int n, int[] count) {
        long result = 0;
        int plus = 0;
        int minus = 0;
        while (plus < n && count[plus] <= 0) plus++;
        while (minus < n && count[minus] >= 0) minus++;
        while (plus < n && minus < n) {
            int distance = Math.abs(plus - minus);
            int buy = Math.min(count[plus], -count[minus]);
            result += (long) distance * buy;
            count[plus] -= buy;
            count[minus] += buy;
            while (plus < n && count[plus] <= 0) plus++;
            while (minus < n && count[minus] >= 0) minus++;
        }
        return result;
    }
}