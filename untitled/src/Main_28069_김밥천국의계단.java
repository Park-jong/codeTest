import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_28069_김밥천국의계단 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        boolean result = isPossible(n, k);
        bw.write(result ? "minigimbob" : "water");
        bw.close();
        br.close();
    }

    static boolean isPossible(int n, int k) {
        int[] stair = new int[n + 1];
        Arrays.fill(stair, Integer.MAX_VALUE);
        stair[0] = 0;
        for (int i = 0; i < n; i++) {
            stair[i + 1] = Math.min(stair[i] + 1, stair[i + 1]);
            int next = i + i / 2;
            if (next <= n) {
                stair[next] = Math.min(stair[i] + 1, stair[next]);
            }
        }
        return stair[n] <= k;
    }
}