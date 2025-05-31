import java.io.*;
import java.util.StringTokenizer;

public class Main_1082_greedy_방번호 {

    static int num[];
    static int n;
    static int m;
    static int minNumber;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }
        m = Integer.parseInt(br.readLine());
        int length = getMaxLength();
        String result = getResult(length);
        bw.write(result);
        bw.close();
        br.close();
    }

    static int getMaxLength() {
        int money = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            min = Math.min(min, num[i]);
        }
        if (min > m) {
            return 0;
        }
        money += min;
        min = Math.min(num[0], min);
        minNumber = min;
        return (m - money) / min + 1;
    }

    static String getResult(int length) {
        if (length == 0) return "0";
        int remain = m;
        int count = length - 1;
        StringBuilder result = new StringBuilder();
        while (count >= 0) {
            for (int i = n - 1; i >= 0; i--) {
                if (possible(count, remain - num[i])) {
                    result.append(i);
                    remain -= num[i];
                    count--;
                    break;
                }
            }
        }
        return result.toString();
    }

    static boolean possible(int count, int remain) {
        return minNumber * count <= remain;
    }
}