import java.io.*;
import java.util.*;

public class Main_32624_신기한루트개수찾기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            nodes[u].next.add(v);
            nodes[v].next.add(u);
        }
        int result = countCommon(n, a, b, nodes);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int countCommon(int n, int a, int b, Node[] nodes) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(a);
        boolean[] isVisitedFromA = new boolean[n + 1];
        isVisitedFromA[a] = true;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            for (int next : nodes[index].next) {
                if (isVisitedFromA[next]) continue;
                if (next == b) continue;
                queue.offer(next);
                isVisitedFromA[next] = true;
            }
        }
        queue = new ArrayDeque<>();
        queue.offer(b);
        boolean[] isVisitedFromB = new boolean[n + 1];
        isVisitedFromB[b] = true;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            for (int next : nodes[index].next) {
                if (isVisitedFromB[next]) continue;
                if (next == a) continue;
                queue.offer(next);
                isVisitedFromB[next] = true;
            }
        }
        int result = 0;
        for (int i = 1; i <= n; i++) {
            if (isVisitedFromA[i] && isVisitedFromB[i]) result++;
        }
        return result;
    }

    static class Node {
        int index;
        List<Integer> next = new ArrayList<>();

        Node(int index) {
            this.index = index;
        }
    }
}