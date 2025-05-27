import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_28106_dp_나무타기 {
    final static int DIVIDE = 998_244_353;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[n];
        for(int i = 0; i < n; i++){
            nodes[i] = new Node(i);
        }
        StringTokenizer st = new StringTokenizer(br.readLine());
        int root = 0;
        for(int i = 0; i < n; i++){
            int num = Integer.parseInt(st.nextToken()) - 1;
            if(num == -1) {
                root = i;
            }
            else{
                nodes[num].children.add(nodes[i]);
            }
        }

        int[] distance = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            distance[i] = Integer.parseInt(st.nextToken());
        }
        for(int i = 0; i < n; i++){
            setNext(nodes[i], nodes[i], 0, distance[i]);
        }
        bw.write(String.valueOf(play(nodes[root])));
        bw.close();
        br.close();
    }

    static int play(Node current){
        if(current.playCount != 0) return current.playCount;
        if(current.children.isEmpty()){
            current.playCount = 1;
            return 1;
        }
        int result = 0;
        for(Node next : current.next){
            result += play(next);
            result %= DIVIDE;
        }
        current.playCount = result;
        return result;
    }

    static void setNext(Node origin, Node current, int depth, int goal){
        if(depth <= goal && depth > 0) {
            origin.next.add(current);
        }
        if(depth == goal) {
            return;
        }
        for(Node child : current.children){
            setNext(origin, child, depth + 1, goal);
        }
    }

    static class Node{
        int playCount;
        int num;
        List<Node> children = new ArrayList<>();
        List<Node> next = new ArrayList<>();
        Node(int num){
            this.num = num;
        }
    }
}