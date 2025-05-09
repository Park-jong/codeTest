import java.io.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_32069_가로등 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        long L = Long.parseLong(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        HashSet<Long> set = new HashSet<>();
        Queue<Node> q = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            long num = Long.parseLong(st.nextToken());
            q.offer(new Node(num, 0));
        }
        int count = 0;

        while (!q.isEmpty() && count < K) {
            Node node = q.poll();
            if (set.contains(node.index)) continue;
            if (!set.contains(node.index - 1) && node.index > 0) q.offer(new Node(node.index - 1, node.dark + 1));
            if (!set.contains(node.index + 1) && node.index < L) q.offer(new Node(node.index + 1, node.dark + 1));
            sb.append(node.dark).append("\n");
            set.add(node.index);
            count++;
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static class Node {
        long index;
        long dark;

        Node(long index, long dark) {
            this.index = index;
            this.dark = dark;
        }
    }
}