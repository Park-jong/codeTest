import java.io.*;
import java.util.StringTokenizer;

public class Main_1756_피자굽기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int d = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int[] oven = new int[d];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < d; i++) {
            oven[i] = Integer.parseInt(st.nextToken());
        }
        int[] pizza = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            pizza[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 1; i < d; i++) {
            if (oven[i - 1] < oven[i]) {
                oven[i] = oven[i - 1];
            }
        }
        int index = d - 1;
        int last = -1;
        for (int i = 0; i < n; i++) {
            while (index >= 0) {
                if (oven[index] >= pizza[i]) {
                    if (i == n - 1) {
                        last = index;
                    } else {
                        index--;
                    }
                    break;
                }
                index--;
            }
        }
        int result = last + 1;
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}