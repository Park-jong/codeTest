import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_2212_센서 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] sensor = new int[n];
        for (int i = 0; i < n; i++) {
            sensor[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMinDistance(n, k, sensor);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMinDistance(int n, int k, int[] sensor) {
        Arrays.sort(sensor);
        Integer[] sub = new Integer[n - 1];
        for (int i = 1; i < n; i++) {
            sub[i - 1] = sensor[i] - sensor[i - 1];
        }
        Arrays.sort(sub, Comparator.reverseOrder());
        int result = sensor[n - 1] - sensor[0];
        for (int i = 0; i < k - 1 && i < n - 1; i++) {
            result -= sub[i];
        }
        return result;
    }
}