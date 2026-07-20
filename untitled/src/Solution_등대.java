import java.util.*;

class Solution_등대 {
    public int solution(int n, int[][] lighthouse) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        link(nodes, lighthouse);
        turnOnLight(nodes);
        int answer = countLight(nodes, lighthouse);
        return answer;
    }

    void link(Node[] nodes, int[][] lighthouse) {
        for (int[] road : lighthouse) {
            int a = road[0] - 1;
            int b = road[1] - 1;
            nodes[a].next.add(nodes[b]);
            nodes[a].link++;
            nodes[b].next.add(nodes[a]);
            nodes[b].link++;
        }
    }

    void turnOnLight(Node[] nodes) {
        Queue<Node> queue = new ArrayDeque<>();
        for (Node node : nodes) {
            if (node.link == 1) {
                queue.offer(node);
                node.isVisited = true;
            }
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Node n : node.next) {
                if (n.isVisited) continue;
                n.light = n.light | !node.light;
                n.link--;
                if (n.link == 1) {
                    queue.offer(n);
                    n.isVisited = true;
                }
            }
        }
    }

    int countLight(Node[] nodes, int[][] lighthouse) {
        int count = 0;
        for (Node node : nodes) {
            if (node.light) count++;
        }
        for (int[] n : lighthouse) {
            Node a = nodes[n[0] - 1];
            Node b = nodes[n[1] - 1];
            if (!a.light && !b.light) count++;
        }
        return count;
    }

    class Node {
        List<Node> next = new ArrayList<>();
        int link = 0;
        boolean light = false;
        boolean isVisited = false;
    }
}