import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_32029_지금자면꿈을꾸지만 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int originCompleteTime = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int[] limit = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            limit[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(limit);
        int result = solveAssignment(limit, n, originCompleteTime, b);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int solveAssignment(int[] limit, int n, int originCompleteTime, int b) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int reduceTime = 0; reduceTime < originCompleteTime; reduceTime++) {
                int time = 0;
                int count = 0;
                for (int k = 0; k < n; k++) {
                    if (i == k) {
                        if (time > limit[k]) {
                            break;
                        }
                        time += reduceTime * b;
                    }
                    int completeTime = originCompleteTime;
                    if (k >= i) {
                        completeTime -= reduceTime;
                    }
                    if (time <= limit[k] - completeTime) {
                        time += completeTime;
                        count++;
                    }
                }
                result = Math.max(result, count);
            }
        }
        return result;
    }

}