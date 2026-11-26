import java.io.*;
import java.util.StringTokenizer;

public class Main_14434_놀이기구1 {
    static int n;
    static int m;
    static int k;
    static int q;
    static int[] limit;
    static int[] grow;
    static int[] tall;
    static int[][] children;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        limit = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            limit[i] = Integer.parseInt(st.nextToken());
        }
        grow = new int[k];
        tall = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            grow[i] = Integer.parseInt(st.nextToken()) - 1;
            tall[grow[i]]++;
        }
        children = new int[n][];
        for (int i = 0; i < n; i++) {
            children[i] = new int[tall[i]];
        }
        for (int i = k - 1; i >= 0; i--) {
            children[grow[i]][--tall[grow[i]]] = i + 1;
        }
        int[] result = new int[k];
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int day = Query(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
            if (day <= k) {
                result[day - 1]++;
            }
        }
        bw.write(sum(result));
        bw.close();
        br.close();
    }

    static int Query(int child1, int child2, int track) {
        int left = 1;
        int right = k;
        int result = k + 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            int c1 = searchGrow(children[child1], mid);
            int c2 = searchGrow(children[child2], mid);
            if (c1 + c2 >= limit[track]) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    static int searchGrow(int[] child, int day) {
        int left = 0;
        int right = child.length - 1;
        int result = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (child[mid] <= day) {
                result = mid + 1;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static String sum(int[] result) {
        StringBuilder sb = new StringBuilder();
        sb.append(result[0]).append("\n");
        for (int i = 1; i < k; i++) {
            result[i] += result[i - 1];
            sb.append(result[i]).append("\n");
        }
        return sb.toString();
    }
}