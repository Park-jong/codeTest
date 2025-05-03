import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_3584_tree_가장가까운공통조상 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < testCase; tc++) {
            int n = Integer.parseInt(br.readLine());
            Node[] nodes = new Node[n + 1];
            for (int i = 1; i <= n; i++) {
                nodes[i] = new Node(i);
            }
            for (int i = 1; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                nodes[child].setParent(nodes[parent]);
            }
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int result = getCommonParent(nodes, a, b);
            sb.append(result).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static int getCommonParent(Node[] nodes, int a, int b) {
        Deque<Integer> stackA = new ArrayDeque<>();
        Deque<Integer> stackB = new ArrayDeque<>();
        addParentInStack(nodes, stackA, a);
        addParentInStack(nodes, stackB, b);
        int commonParent = 0;
        while (!stackA.isEmpty() && !stackB.isEmpty()) {
            int parentA = stackA.pollLast();
            int parentB = stackB.pollLast();
            if (parentA != parentB) break;
            commonParent = parentA;
        }
        return commonParent;
    }

    static void addParentInStack(Node[] nodes, Deque<Integer> stack, int num) {
        Node current = nodes[num];
        stack.offer(num);
        while (current.parent != null) {
            current = current.parent;
            stack.offer(current.num);
        }
    }

    static class Node {
        int num;
        Node parent;

        Node(int num) {
            this.num = num;
        }

        void setParent(Node parent) {
            this.parent = parent;
        }
    }
}