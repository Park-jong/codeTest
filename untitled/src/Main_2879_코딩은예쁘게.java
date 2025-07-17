import java.io.*;
import java.util.StringTokenizer;

public class Main_2879_코딩은예쁘게 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] origin = new int[n + 1];
        int[] fix = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            origin[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            fix[i] = Integer.parseInt(st.nextToken());
        }
        int result = getFixedCount(origin, fix, n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int getFixedCount(int[] origin, int[] fix, int n) {
        int[] sub = new int[n + 1];
        int[] absSub = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sub[i] = origin[i] - fix[i];
            absSub[i] = Math.abs(sub[i]);
        }
        int prev = sub[0];
        int startIndex = 0;
        int endIndex = 0;
        int result = 0;
        for (int i = 1; i <= n; i++) {
            int cur = sub[i];
            if (sameSign(cur, prev)) {
                endIndex = i;
            } else {
                result += checkGroupFixCount(absSub, startIndex, endIndex);
                startIndex = i;
                endIndex = i;
            }
            prev = cur;
        }
        return result;
    }

    static boolean sameSign(int cur, int prev) {
        return (cur > 0 && prev > 0) || (cur < 0 && prev < 0);
    }

    static int checkGroupFixCount(int[] absSub, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return absSub[startIndex];
        }
        int result = 0;
        while (true) {
            int min = Integer.MAX_VALUE;
            boolean isGroup = false;
            int groupCount = 0;
            for (int i = startIndex; i <= endIndex; i++) {
                if (absSub[i] == 0) {
                    continue;
                }
                min = Math.min(absSub[i], min);
            }
            for (int i = startIndex; i <= endIndex; i++) {
                if (absSub[i] == 0) {
                    isGroup = false;
                    continue;
                }
                if(!isGroup) {
                    isGroup = true;
                    groupCount++;
                }
                absSub[i] -= min;
            }
            result += min * groupCount;
            if(min == Integer.MAX_VALUE){
                break;
            }
        }
        return result;
    }
}