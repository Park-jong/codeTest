import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_25606_장마 {

    static int n;
    static int m;
    static int q;
    static int[] box;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        box = new int[n];
        Water[] water = new Water[n + 1];
        for (int i = 0; i <= n; i++) {
            water[i] = new Water();
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            box[i] = Integer.parseInt(st.nextToken());
            int removeDay = box[i] / m + i + 2;
            if (removeDay <= n) {
                water[removeDay].removeIndex.add(i);
            }
        }
        int[][] query = new int[q][2];
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            query[i][0] = Integer.parseInt(st.nextToken());
            query[i][1] = Integer.parseInt(st.nextToken());
        }
        String result = getResultString(water, query);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String getResultString(Water[] water, int[][] query) {
        getQueryResult(water);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int type = query[i][0];
            int day = query[i][1];
            if (type == 1) {
                result.append(getRemain(water, day));
            } else {
                result.append(getRemove(water, day));
            }
            result.append("\n");
        }
        return result.toString();
    }

    static void getQueryResult(Water[] water) {
        for (int i = 1; i <= n; i++) {
            water[i].remainCount = water[i - 1].remainCount - water[i].removeIndex.size() + 1;
            water[i].removeSum = (water[i].remainCount - 1) * m;
            for (int index : water[i].removeIndex) {
                water[i].removeSum += box[index] % m;
            }
            water[i].remainSum = box[i - 1] + water[i - 1].remainSum - water[i].removeSum;
        }
    }

    static int getRemain(Water[] water, int day) {
        return water[day].remainSum;
    }

    static int getRemove(Water[] water, int day) {
        return water[day].removeSum;
    }

    static class Water {
        int remainSum;
        int removeSum;
        int remainCount;
        List<Integer> removeIndex = new ArrayList<>();
    }
}