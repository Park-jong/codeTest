import java.io.*;
import java.util.StringTokenizer;

public class Main_1581_락스타락동호 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int FF = Integer.parseInt(st.nextToken());
        int FS = Integer.parseInt(st.nextToken());
        int SF = Integer.parseInt(st.nextToken());
        int SS = Integer.parseInt(st.nextToken());

        int result = 0;
        if (FS > 0) {
            if (FS > SF) {
                result = SF * 2 + 1;
            } else {
                result = FS * 2;
            }
            result += FF + SS;
        } else if (FF > 0) {
            result = FF;
        } else {
            result = SS;
            if (SF > 0) {
                result++;
            }
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
}