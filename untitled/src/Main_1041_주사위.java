import java.io.*;
import java.util.StringTokenizer;

public class Main_1041_주사위 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] dice = new int[6];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 6; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }
        int temp1 = dice[3];
        int temp2 = dice[5];
        dice[3] = temp2;
        dice[5] = temp1;
        int[] min = minSum(dice);
        long result = (long) (n - 2) * (n - 2) * min[0] * 5 + (long) min[1] * (n - 2) * 8 + (long) min[2] * 4 + (long) (n - 2) * 4 * min[0] + (long) min[1] * 4;
        if(n == 1){
            result = min[3];
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int[] minSum(int[] dice) {
        int[] min = new int[4];
        min[0] = Integer.MAX_VALUE;
        min[1] = Integer.MAX_VALUE;
        min[2] = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < 6; i++) {
            min[0] = Math.min(min[0], dice[i]);
            min[3] += dice[i];
            max = Math.max(max, dice[i]);
            for (int j = 0; j < 6; j++) {
                if (i % 3 == j % 3) continue;
                min[1] = Math.min(min[1], dice[i] + dice[j]);
                for (int k = 0; k < 6; k++) {
                    if (i % 3 == k % 3 || j % 3 == k % 3) continue;
                    min[2] = Math.min(min[2], dice[i] + dice[j] + dice[k]);
                }
            }
        }
        min[3] -= max;

        return min;
    }
}