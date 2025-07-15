import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_3020_개똥벌레 {

    static int minDestroy = Integer.MAX_VALUE;
    static int minValueCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int[] bottom = new int[n / 2 + n % 2];
        int[] top = new int[n / 2];
        for (int i = 0, j = 0, k = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            if (i % 2 == 0) {
                bottom[j++] = num;
            } else {
                top[k++] = num;
            }
        }
        Arrays.sort(bottom);
        Arrays.sort(top);
        getMinDestroyCount(bottom, top, h);
        String result = minDestroy + " " + minValueCount;
        bw.write(result);
        bw.close();
        br.close();
    }

    static void getMinDestroyCount(int[] bottom, int[] top, int h) {
        for (int i = 1; i <= h; i++) {
            int bottomDestroy = binarySearchDestroyCount(bottom, i);
            int topDestroy = binarySearchDestroyCount(top, h + 1 - i);
            int total = topDestroy + bottomDestroy;
            if (total < minDestroy) {
                minDestroy = total;
                minValueCount = 1;
            } else if (total == minDestroy) {
                minValueCount++;
            }
        }
    }

    static int binarySearchDestroyCount(int[] array, int h) {
        int size = array.length;
        int left = 0;
        int right = size - 1;
        int notDestroyCount = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (array[mid] >= h) {
                right = mid - 1;
            } else {
                notDestroyCount = mid + 1;
                left = mid + 1;
            }
        }
        return size - notDestroyCount;
    }

}