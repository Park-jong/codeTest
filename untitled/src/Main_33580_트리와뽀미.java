import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_33580_트리와뽀미 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            nodes[a].link.add(b);
            nodes[b].link.add(a);
        }
        int[] c = new int[t + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= t; i++) {
            c[i] = Integer.parseInt(st.nextToken());
        }
        int result = findMaxMeet(n, t, nodes, c);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxMeet(int n, int t, Node[] nodes, int[] c) {
        int[][] count = new int[t + 1][n + 1];
        for (int i = 1; i <= t; i++) {
            int cat = c[i];
            for (int j = 1; j <= n; j++) {
                count[i][j] = count[i - 1][j];
                for (int link : nodes[j].link) {
                    count[i][j] = Math.max(count[i][j], count[i - 1][link]);
                }
            }
            count[i][cat]++;
        }
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result = Math.max(result, count[t][i]);
        }
        return result;
    }

    static class Node {
        List<Integer> link = new ArrayList<>();
    }
}