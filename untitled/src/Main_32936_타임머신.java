import java.io.*;
import java.util.*;

public class Main_32936_타임머신 {

    static int MAX_VALUE = Integer.MAX_VALUE >> 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        List<Integer>[] edges = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            edges[u].add(v);
        }
        String result = findMinTime(n, edges, a, b, c);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String findMinTime(int n, List<Integer>[] edges, int a, int b, int c) {
        int[] distance1 = distance(n, 1, edges);
        int[] distanceB = distance(n, b, edges);
        if (distance1[n] == MAX_VALUE && (distance1[a] == MAX_VALUE || distanceB[n] == MAX_VALUE)) return "x";
        if (distance1[a] != MAX_VALUE && distanceB[a] < c && distanceB[n] != MAX_VALUE) return "-inf";
        return String.valueOf(Math.min(distance1[n], distance1[a] - c + distanceB[n]));
    }

    static int[] distance(int n, int index, List<Integer>[] edges) {
        int[] distance = new int[n + 1];
        boolean[] isVisited = new boolean[n + 1];
        Arrays.fill(distance, MAX_VALUE);
        distance[index] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{index, 0});
        isVisited[index] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            List<Integer> list = edges[current[0]];
            for (int i : list) {
                if (isVisited[i]) continue;
                queue.offer(new int[]{i, current[1] + 1});
                isVisited[i] = true;
                distance[i] = current[1] + 1;
            }
        }
        return distance;
    }
}