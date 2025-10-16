import java.io.*;
import java.util.StringTokenizer;

public class Main_12924_멋진쌍 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int n = String.valueOf(a).length();
        int count = 0;
        for (int i = a; i <= b; i++) {
            for (int j = 1; j < n; j++) {
                String str = String.valueOf(i);
                String change = str.substring(j, n) + str.substring(0, j);
                int c = Integer.parseInt(change);
                if (i == c) {
                    break;
                }
                if (i < c && c <= b) {
                    count++;
                }
            }
        }
        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }
}