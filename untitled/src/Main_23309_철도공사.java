import java.io.*;
import java.util.StringTokenizer;

public class Main_23309_철도공사 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        Node[] nodes = new Node[1000001];
        Node link = null;
        Node start = null;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            Node current = nodes[num] = new Node(num);
            if(start == null){
                start = current;
            }
            else {
                current.prev = link;
                link.next = current;
            }
            if(i == n - 1){
                current.next = start;
                start.prev = current;
            }
            link = current;
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            int result = 0;
            switch (command) {
                case "BN": {
                    int node = Integer.parseInt(st.nextToken());
                    int newNode = Integer.parseInt(st.nextToken());
                    result = BN(node, newNode, nodes);
                    break;
                }
                case "BP": {
                    int node = Integer.parseInt(st.nextToken());
                    int newNode = Integer.parseInt(st.nextToken());
                    result = BP(node, newNode, nodes);
                    break;
                }
                case "CN": {
                    int node = Integer.parseInt(st.nextToken());
                    result = CN(node, nodes);
                    break;
                }
                case "CP":{
                    int node = Integer.parseInt(st.nextToken());
                    result = CP(node, nodes);
                    break;
                }
                default:
                    break;
            }
            sb.append(result).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static int BN(int node, int newNode, Node[] nodes) {
        nodes[newNode] = new Node(newNode);
        return nodes[newNode].buildNext(nodes[node]);
    }

    static int BP(int node, int newNode, Node[] nodes) {
        nodes[newNode] = new Node(newNode);
        return nodes[newNode].buildPrev(nodes[node]);
    }

    static int CN(int node, Node[] nodes){
        Node next = nodes[node].next;
        return next.remove();
    }

    static int CP(int node, Node[] nodes){
        Node prev = nodes[node].prev;
        return prev.remove();
    }

    static class Node {
        int num;
        Node next;
        Node prev;

        Node(int num) {
            this.num = num;
        }

        int remove() {
            next.prev = prev;
            prev.next = next;
            return num;
        }

        int buildNext(Node node) {
            this.next = node.next;
            this.prev = node;
            node.next = this;
            next.prev = this;
            return next.num;
        }

        int buildPrev(Node node) {
            this.prev = node.prev;
            this.next = node;
            node.prev = this;
            prev.next = this;
            return prev.num;
        }
    }
}