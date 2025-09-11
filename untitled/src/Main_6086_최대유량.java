import java.io.*;
import java.util.*;

public class Main_6086_최대유량 {

    static int n;
    static int[][] pipe;
    static Map<String, Integer> map;
    static String[] pipeName;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        pipe = new int[n][n];
        map = new HashMap<>();
        pipeName = new String[n];
        int index = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            if (!map.containsKey(a)) {
                pipeName[index] = a;
                map.put(a, index++);
            }
            String b = st.nextToken();
            if (!map.containsKey(b)) {
                pipeName[index] = b;
                map.put(b, index++);
            }
            int c = Integer.parseInt(st.nextToken());
            pipe[map.get(a)][map.get(b)] += c;
            pipe[map.get(b)][map.get(a)] += c;
        }
        int result = findMaxFlow();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxFlow() {
        int start = map.get("A");
        int goal = map.get("Z");
        int result = 0;
        while (true) {
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(start);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                for (int i = 0; i < n; i++) {
                    if (pipe[current][i] > 0 && parent[i] == -1) {
                        parent[i] = current;
                        queue.offer(i);
                    }
                }
            }
            if (parent[goal] == -1) {
                break;
            }
            int flow = Integer.MAX_VALUE;
            for (int node = goal; node != start; node = parent[node]) {
                flow = Math.min(flow, pipe[parent[node]][node]);
            }
            for (int node = goal; node != start; node = parent[node]) {
                pipe[parent[node]][node] -= flow;
                pipe[node][parent[node]] += flow;
            }
            result += flow;
        }
        return result;
    }
}