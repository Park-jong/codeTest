import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_2515_전시장 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        Picture[] pictures = new Picture[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            pictures[i] = new Picture(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        int result = findMaxValue(n, s, pictures);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxValue(int n, int s, Picture[] pictures) {
        Arrays.sort(pictures, Comparator.comparingInt(a -> a.h));
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], addValue(s, pictures, dp, i));
        }
        return dp[n];
    }

    static int addValue(int s, Picture[] pictures, int[] dp, int i) {
        return dp[binarySearch(pictures, pictures[i - 1].h - s) + 1] + pictures[i - 1].c;
    }

    static int binarySearch(Picture[] pictures, int height) {
        int left = 0;
        int right = pictures.length - 1;
        int result = -1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (pictures[mid].h <= height) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static class Picture {
        int h;
        int c;

        Picture(int h, int c) {
            this.h = h;
            this.c = c;
        }
    }
}