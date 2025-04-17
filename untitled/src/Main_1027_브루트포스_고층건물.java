import java.io.*;
import java.util.StringTokenizer;

public class Main_1027_브루트포스_고층건물 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int[] building = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            building[i] = Integer.parseInt(st.nextToken());
        }
        int[] count = new int[n];
        int result = 0;
        for (int i = 0; i < n; i++) {
            long gradientX = 0;
            long gradientY = 0;
            for (int j = i + 1; j < n; j++) {
                if (j == i + 1) {
                    gradientX = j - i;
                    gradientY = building[j] - building[i];
                    count[i]++;
                    continue;
                }
                long subX = j - i;
                long subY = building[j] - building[i];
                if (subY * gradientX > subX * gradientY) {
                    gradientX = subX;
                    gradientY = subY;
                    count[i]++;
                }
            }
            gradientX = 0;
            gradientY = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (j == i - 1) {
                    gradientX = j - i;
                    gradientY = building[j] - building[i];
                    count[i]++;
                    continue;
                }
                long subX = j - i;
                long subY = building[j] - building[i];
                if (subY * gradientX < subX * gradientY) {
                    gradientX = subX;
                    gradientY = subY;
                    count[i]++;
                }
            }
            result = Math.max(result, count[i]);
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }
}