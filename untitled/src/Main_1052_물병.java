import java.io.*;
import java.util.StringTokenizer;

public class Main_1052_물병 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int result = findBottlesToBuy(n, k);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findBottlesToBuy(int n, int k) {
        int[] array = new int[k];
        int sum = 0;
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            while (sum < n) {
                if (array[i] == 0) {
                    array[i] = 1;
                    sum += 1;
                } else {
                    sum += array[i];
                    array[i] = array[i] << 1;
                }
            }
            result = Math.min(result, sum - n);
            array[i] = array[i] >> 1;
            sum -= array[i];
        }
        return result;
    }
}