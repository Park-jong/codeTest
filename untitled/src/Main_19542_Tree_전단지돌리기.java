import java.io.*;
import java.util.*;

public class Main_19542_Tree_전단지돌리기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            nodes[x].link.add(nodes[y]);
            nodes[y].link.add(nodes[x]);
        }

        makeTree(nodes, n, s);
        List<Node> leaves = findLeaf(nodes);
        setDepth(leaves);
        int result = distance(nodes, n, s, d);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }


    static void makeTree(Node[] nodes, int n, int s) {
        boolean[] isVisited = new boolean[n + 1];
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(nodes[s]);
        isVisited[s] = true;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Node child : node.link) {
                if (!isVisited[child.num]) {
                    node.children.add(child);
                    child.parent = node;
                    isVisited[child.num] = true;
                    queue.add(child);
                }
            }
        }
    }

    static List<Node> findLeaf(Node[] nodes) {
        int n = nodes.length;
        List<Node> list = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (nodes[i].children.isEmpty()) list.add(nodes[i]);
        }
        return list;
    }

    static void setDepth(List<Node> leaves) {
        for (Node node : leaves) {
            Node current = node;
            while (true) {
                if (current.parent == null) break;
                if (current.parent.maxDepth >= current.maxDepth + 1) break;
                current.parent.maxDepth = current.maxDepth + 1;
                current = current.parent;
            }
        }
    }

    static int distance(Node[] nodes, int n, int s, int d) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            if (i == s) continue;
            if (nodes[i].maxDepth >= d) result += 2;
        }
        return result;
    }

    static class Node {
        int num;
        int maxDepth;
        Node parent;
        List<Node> link = new ArrayList<>();
        List<Node> children = new ArrayList<>();

        Node(int num) {
            this.num = num;
        }
    }
}