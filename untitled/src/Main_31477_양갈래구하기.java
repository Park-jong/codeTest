import java.io.*;
import java.util.*;

public class Main_31477_양갈래구하기 {

    static int n;
    static Node[] nodes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cut = Integer.parseInt(st.nextToken());
            Edge e = new Edge(a, b, cut);
            nodes[a].link.add(e);
            nodes[b].link.add(e);
            nodes[a].count++;
            nodes[b].count++;
        }
        setParent();
        int result = getMinCut();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static void setParent() {
        Node root = nodes[1];
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Edge edge : node.link) {
                int next = getNext(edge, node);
                if (next == node.parent) {
                    node.cut = edge.cut;
                    continue;
                }
                nodes[next].parent = node.num;
                nodes[next].count--;
                queue.offer(nodes[next]);
            }
        }
    }

    static int getNext(Edge edge, Node node) {
        int next = edge.a;
        if (edge.a == node.num) {
            next = edge.b;
        }
        return next;
    }

    static int getMinCut(){
        Queue<Node> queue = new ArrayDeque<>();
        for(int i = 2; i <= n; i++){
            if(nodes[i].count == 0){
                queue.offer(nodes[i]);
            }
        }
        while(!queue.isEmpty()){
            Node node = queue.poll();
            if(node.num == 1) break;
            if(node.sum > 0 && node.sum < node.cut){
                node.cut = node.sum;
            }
            Node parent = nodes[node.parent];
            parent.sum += node.cut;
            parent.count--;
            if(parent.count == 0){
                queue.offer(parent);
            }
        }
        return nodes[1].sum;
    }


    static class Node {
        int num;
        int count;
        int parent;
        int cut = Integer.MAX_VALUE;
        int sum;
        List<Edge> link = new ArrayList<>();

        Node(int num) {
            this.num = num;
        }
    }

    static class Edge {
        int a;
        int b;
        int cut;

        Edge(int a, int b, int cut) {
            this.a = a;
            this.b = b;
            this.cut = cut;
        }
    }
}