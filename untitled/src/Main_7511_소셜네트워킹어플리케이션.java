import java.io.*;
import java.util.StringTokenizer;

public class Main_7511_소셜네트워킹어플리케이션 {
    static int[] set;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int scenario = Integer.parseInt(br.readLine());
        for (int scen = 0; scen < scenario; scen++) {
            sb.append("Scenario ").append(scen + 1).append(":\n");
            int n = Integer.parseInt(br.readLine());
            int k = Integer.parseInt(br.readLine());
            makeSet(n);
            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                union(a, b);
            }
            int m = Integer.parseInt(br.readLine());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                if (findSet(a) == findSet(b)) sb.append(1);
                else sb.append(0);
                sb.append("\n");
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void makeSet(int n) {
        set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
    }

    static int findSet(int n) {
        if (set[n] == n) return n;
        return set[n] = findSet(set[n]);
    }

    static boolean union(int a, int b) {
        int rootA = findSet(a);
        int rootB = findSet(b);
        if (rootB == rootA) return false;
        set[rootB] = set[rootA];
        return true;
    }
}