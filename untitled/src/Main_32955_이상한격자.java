import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_32955_이상한격자 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] stamina = new int[4];
        for (int i = 0; i < 4; i++) {
            stamina[i] = Integer.parseInt(st.nextToken());
        }
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }
        long result = findMinStamina(n, stamina, x, y);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findMinStamina(int n, int[] stamina, int[] x, int[] y) {
        Arrays.sort(x);
        Arrays.sort(y);
        long minSum = getMinSum(n, x, stamina[0], stamina[1]);
        long minSum2 = getMinSum(n, y, stamina[2], stamina[3]);
        return minSum + minSum2;
    }

    private static long getMinSum(int n, int[] array, int staminaLeft, int staminaRight) {
        long leftSum = 0;
        long rightSum = 0;
        for (int i = 0; i < n; i++) {
            rightSum += (long) (array[i] - array[0]) * staminaLeft;
        }
        long minSum = leftSum + rightSum;
        int left = 0, right = 0;
        while (right < n && array[right] <= array[0]) right++;
        for (int i = array[0]; i <= array[n - 1]; i++) {
            while (left < n && array[left] < i) left++;
            while (right < n && array[right] <= i) right++;
            leftSum += (long) staminaRight * left;
            minSum = Math.min(minSum, rightSum + leftSum);
            rightSum -= (long) (n - right) * staminaLeft;
        }
        return minSum;
    }
}