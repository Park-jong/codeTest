import java.io.*;
import java.util.StringTokenizer;

public class Main_33934_징검다리의징검다리 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken()) - 1;
        int e = Integer.parseInt(st.nextToken()) - 1;
        int[] lake = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            lake[i] = Integer.parseInt(st.nextToken());
        }
        int k = Integer.parseInt(br.readLine());
        int result = isPossible(lake, n, s, e, k);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int isPossible(int[] lake, int n, int s, int e, int k) {
        int small = Math.min(s, e);
        int big = Math.max(s, e);
        int count = 0;
        for (int i = small + 1; i < big; i++) {
            count += lake[i];
        }
        for (int i = small; i >= 0; i--) {
            count += lake[i];
            if (lake[i] == 1) {
                break;
            }
        }
        for (int i = big; i < n; i++) {
            count += lake[i];
            if (lake[i] == 1) {
                break;
            }
        }
        return (count >= k) && (big - small + 1 <= k) ? 1 : 0;
    }
}