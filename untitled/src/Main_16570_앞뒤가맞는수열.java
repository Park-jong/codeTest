import java.io.*;
import java.util.StringTokenizer;

public class Main_16570_앞뒤가맞는수열 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        int[] length = commonLength(n, array);
        String result = (length[0] == 0) ? "-1" : (String.format("%d %d", length[0], length[1]));
        bw.write(result);
        bw.close();
        br.close();
    }

    static int[] commonLength(int n, int[] array) {
        int[] reverse = new int[n];
        for (int i = 0; i < n; i++) {
            reverse[n - i - 1] = array[i];
        }
        int[] length = new int[n];
        int maxLength = 0;
        int count = 0;
        int i = 0;
        for (int j = i + 1; j < n; j++) {
            while (reverse[i] != reverse[j] && i > 0) {
                i = length[i - 1];
            }
            if (reverse[i] == reverse[j]) {
                length[j] = ++i;
                if (i == maxLength) {
                    count++;
                } else if (i > maxLength) {
                    count = 1;
                    maxLength = i;
                }
            }
        }
        return new int[]{maxLength, count};
    }
}