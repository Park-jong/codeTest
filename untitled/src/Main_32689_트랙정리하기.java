import java.io.*;
import java.util.StringTokenizer;

public class Main_32689_트랙정리하기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] trash = new int[n];
        for (int i = 0; i < n; i++) {
            trash[i] = Integer.parseInt(st.nextToken());
        }
        long result = moveDistance(n, trash);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long moveDistance(int n, int[] trash) {
        int left = 0;
        int right = n - 1;
        int sum = 0;
        for (int t : trash) {
            sum += t;
        }
        if (sum == 0) return 0;
        long distance = 0;
        while (true) {
            while (left < n && trash[left] == 0) {
                left++;
            }
            while (right > 0 && trash[right] == 0) {
                right--;
            }
            if (left > right) break;
            if (left == right) {
                distance += ((long) n * (trash[left] - 1) + left);
                break;
            }
            int min = Math.min(trash[left], trash[right]);
            distance += ((long) (min) * (left + n - right)) * 2 - n + right;
            trash[left] -= min;
            trash[right] -= min;
            sum -= min * 2;
            if (sum > 0) {
                distance += n - right;
            }
        }
        return distance;
    }
}