import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_28076_멀티탭충분하다 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        Integer[] multi = new Integer[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            multi[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(multi, (a, b) -> Integer.compare(b, a));
        int divide = N / 3;
        int remain = N % 3;
        int index2 = divide + (remain > 0 ? 1 : 0);
        int index3 = index2 + divide + (remain > 1 ? 1 : 0);
        int result = multi[0];
        if (N > 1) {
            result += multi[index2] - 1;
        }
        if (N > 2) {
            result += multi[index3] - 2;
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}