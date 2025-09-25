import java.io.*;
import java.util.StringTokenizer;

public class Main_26607_시로코와은행털기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
            b[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMaxPower(n, k, x, a, b);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxPower(int n, int k, int x, int[] a, int[] b) {
        int p = x * k;
        boolean[][] isPossible = new boolean[k][p + 1];
        for (int i = 0; i < n; i++) {
            for (int j = k - 1; j >= 0; j--) {
                if (j == 0) {
                    isPossible[j][a[i]] = true;
                    continue;
                }
                for (int power = a[i]; power <= p; power++) {
                    if (isPossible[j - 1][power - a[i]]) {
                        isPossible[j][power] = true;
                    }
                }
            }
        }
        int result = 0;
        for (int i = 0; i <= p; i++) {
            if (isPossible[k - 1][i]) {
                result = Math.max(result, i * (p - i));
            }
        }
        return result;
    }
}