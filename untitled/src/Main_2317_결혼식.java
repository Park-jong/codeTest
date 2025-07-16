import java.io.*;
import java.util.StringTokenizer;

public class Main_2317_결혼식 {

    static int[] lions;
    static int[] people;
    static int max = 0;
    static int min = Integer.MAX_VALUE;
    static int lionMax = 0;
    static int lionMin = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        lions = new int[k];
        people = new int[n - k];
        for (int i = 0; i < k; i++) {
            int height = Integer.parseInt(br.readLine());
            lions[i] = height;
            lionMax = Math.max(lionMax, height);
            lionMin = Math.min(lionMin, height);
        }
        for (int i = k; i < n; i++) {
            int height = Integer.parseInt(br.readLine());
            people[i - k] = height;
            max = Math.max(height, max);
            min = Math.min(height, min);
        }
        long result = getMinSub();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long getMinSub() {
        long subSum = 0;
        int n = lions.length;
        int m = people.length;
        int current = lions[0];
        boolean maxUsed = false;
        boolean minUsed = false;
        if (m > 0) {
            if (min >= lionMin) {
                minUsed = true;
            }
            if (max <= lionMax) {
                maxUsed = true;
            }
        }
        for (int lion : lions) {
            subSum += Math.abs(current - lion);
            current = lion;
        }
        if (m > 0 && !minUsed) {
            subSum += findMinPosition(n);
        }
        if (m > 0 && !maxUsed) {
            subSum += findMaxPosition(n);
        }
        return subSum;
    }

    static long findMinPosition(int n) {
        long findMinValue = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            long sub = Math.abs((long) Math.min(lions[i], lions[i - 1]) - min) * 2L;
            findMinValue = Math.min(sub, findMinValue);
        }
        findMinValue = Math.min(findMinValue, (long) lions[n - 1] - min);
        findMinValue = Math.min(findMinValue, (long) lions[0] - min);
        return findMinValue;
    }

    static long findMaxPosition(int n) {
        long findMinValue = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            long sub = Math.abs((long) max - (Math.max(lions[i], lions[i - 1]))) * 2L;
            findMinValue = Math.min(sub, findMinValue);
        }
        findMinValue = Math.min(findMinValue, Math.abs((long) max - lions[n - 1]));
        findMinValue = Math.min(findMinValue, Math.abs((long) max - lions[0]));
        return findMinValue;
    }
}