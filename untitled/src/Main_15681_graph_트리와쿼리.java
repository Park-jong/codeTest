import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_15681_graph_트리와쿼리 {
    static Node[] nodes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(st.nextToken());
        int root = Integer.parseInt(st.nextToken());
        int query = Integer.parseInt(st.nextToken());
        nodes = new Node[n + 1];
        for (int i = 0; i < n + 1; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            link(nodes[n1], nodes[n2]);
        }
        nodes[root].setRoot();

        for(int i = 0; i < query; i++){
            int q = Integer.parseInt(br.readLine());
            sb.append(nodes[q].nodeCount).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static void link(Node n1, Node n2) {
        n1.link.add(n2.num);
        n2.link.add(n1.num);
    }

    private static class Node {
        int parent = -1;
        List<Integer> children = new ArrayList();
        List<Integer> link = new ArrayList<>();
        int nodeCount = 1;
        int num;

        Node(int num) {
            this.num = num;
        }

        void setRoot() {
            for (int n : link) {
                children.add(n);
                nodes[n].setParent(num);
                nodeCount += nodes[n].nodeCount;

            }
        }

        void setParent(int parent) {
            this.parent = parent;
            for (int n : link) {
                if (n != parent) {
                    children.add(n);
                    nodes[n].setParent(num);
                    nodeCount += nodes[n].nodeCount;
                }
            }
        }
    }
}