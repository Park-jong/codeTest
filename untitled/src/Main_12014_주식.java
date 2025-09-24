import java.io.*;
import java.util.StringTokenizer;

public class Main_12014_주식 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for (int i = 1; i <= t; i++) {
            sb.append(String.format("Case #%d\n", i));
            boolean result = solve(br);
            sb.append(result ? 1 : 0).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static boolean solve(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] array = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        int[] buy = new int[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            int index = binarySearch(buy, size, array[i]);
            if (index >= size) {
                buy[size++] = array[i];
            } else {
                buy[index] = array[i];
            }
        }
        return k <= size;
    }

    static int binarySearch(int[] buy, int size, int num) {
        int left = 0;
        int right = size - 1;
        int result = size;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (buy[mid] >= num) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}