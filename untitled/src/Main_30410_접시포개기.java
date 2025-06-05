import java.io.*;
import java.util.StringTokenizer;

public class Main_30410_접시포개기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] nums = new int[n + 1];
        int numCheck = -1;
        int sum = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            if (num != numCheck) {
                if (nums[index] % 2 == 0 && sum % 2 == 0) {
                    nums[index] += sum;
                } else {
                    nums[++index] = sum;
                }
                sum = num;
                numCheck = num;
            } else {
                sum += num;
            }
        }
        if (nums[index] % 2 == 0 && sum % 2 == 0) {
            nums[index] += sum;
        } else {
            nums[++index] = sum;
        }
        int maxSum = 0;
        for (int i = 0; i <= index; i++) {
            if (nums[i] % 2 == 1) {
                maxSum = Math.max(maxSum, nums[i]);
            } else {
                sum = nums[i];
                if (i > 0) {
                    sum += nums[i - 1] - 1;
                }
                if (i < index) {
                    sum += nums[i + 1] - 1;
                }
                maxSum = Math.max(maxSum, sum);
            }
        }
        int result = 1;
        while(result <= maxSum){
            result *= 2;
        }
        result /= 2;
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}