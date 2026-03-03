import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_28071_승형이의사탕사기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] candies = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            candies[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMaxCandies(m, k, candies);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxCandies(int m, int k, int[] candies) {
        int[] prev = new int[k];
        int[] count = new int[k];
        for (int i = 0; i < m; i++) {
            for (int c = 0; c < k; c++) {
                if (prev[c] == 0 && c != 0) continue;
                for (int candy : candies) {
                    int remain = (c + candy) % k;
                    count[remain] = Math.max(prev[c] + candy, count[remain]);
                }
            }
            prev = Arrays.copyOf(count, k);
        }
        return count[0];
    }
}