import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_25606_장마 {

    static int n;
    static int m;
    static int q;
    static int[] box;
    static int[] remainSum;
    static int[] removeSum;
    static int[] remainCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        box = new int[n];
        st = new StringTokenizer(br.readLine());
        remainSum = new int[n + 1];
        removeSum = new int[n + 1];
        remainCount = new int[n + 1];
        for (int i = 0; i < n; i++) {
            box[i] = Integer.parseInt(st.nextToken());
            int removeDay = box[i] / m + i + 2;
            if (removeDay <= n) {
                remainCount[removeDay]--;
                removeSum[removeDay] += box[i] % m;
            }
        }
        int[][] query = new int[q][2];
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            query[i][0] = Integer.parseInt(st.nextToken());
            query[i][1] = Integer.parseInt(st.nextToken());
        }
        String result = getResultString(query);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String getResultString(int[][] query) {
        getQueryResult();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int type = query[i][0];
            int day = query[i][1];
            if (type == 1) {
                result.append(remainSum[day]);
            } else {
                result.append(removeSum[day]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    static void getQueryResult() {
        for (int i = 1; i <= n; i++) {
            remainCount[i] += remainCount[i - 1] + 1;
            removeSum[i] += (remainCount[i] - 1) * m;
            remainSum[i] = box[i - 1] + remainSum[i - 1] - removeSum[i];
        }
    }

}