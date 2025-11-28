import java.io.*;
import java.util.*;

public class Main_28333_화이트칼라 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            sb.append(solve(br)).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static String solve(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[n + 1];
        for (int i = 0; i <= n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            nodes[a].next.add(b);
        }
        boolean[] isVisited = nextPath(nodes);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (isVisited[i]) {
                sb.append(i).append(" ");
            }
        }
        return sb.toString();
    }

    static boolean[] nextPath(Node[] nodes) {
        int n = nodes.length - 1;
        Queue<Move> queue = new ArrayDeque<>();
        queue.offer(new Move(1, 0));
        int[] move = new int[n + 1];
        Arrays.fill(move, Integer.MAX_VALUE);
        move[1] = 0;
        int count = Integer.MAX_VALUE;
        while (!queue.isEmpty() && queue.peek().count <= count) {
            Move current = queue.poll();
            for (int i : nodes[current.index].next) {
                if (move[i] < current.count + 1) continue;
                nodes[i].prev.add(current.index);
                if (i == n) count = current.count + 1;
                if (move[i] == Integer.MAX_VALUE) {
                    queue.offer(new Move(i, current.count + 1));
                    move[i] = current.count + 1;
                }
            }
        }
        boolean[] isVisited = new boolean[n + 1];
        Queue<Integer> path = new ArrayDeque<>();
        path.offer(n);
        isVisited[n] = true;
        while (!path.isEmpty()) {
            int current = path.poll();
            for (int i : nodes[current].prev) {
                if (isVisited[i]) continue;
                isVisited[i] = true;
                path.offer(i);
            }
        }
        return isVisited;
    }

    static class Node {
        List<Integer> next = new ArrayList<>();
        List<Integer> prev = new ArrayList<>();
    }

    static class Move {
        int index;
        int count;

        Move(int index, int count) {
            this.index = index;
            this.count = count;
        }
    }
}