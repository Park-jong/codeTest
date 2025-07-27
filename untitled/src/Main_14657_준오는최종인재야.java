import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_14657_준오는최종인재야 {

    static Node[] nodes;
    static int maxCount;
    static int minTime;
    static int index;
    static boolean[] isVisited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        nodes = makeNodes(br, n);
        int result = findMinDay(n, t);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static Node[] makeNodes(BufferedReader br, int n) throws IOException {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 1; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            nodes[a].link.add(new Next(b, c));
            nodes[b].link.add(new Next(a, c));
        }
        return nodes;
    }

    static int findMinDay(int n, int t) {
        isVisited = new boolean[n];
        findMaxSolveCount(0, 0, 0);
        maxCount = 0;
        isVisited = new boolean[n];
        findMaxSolveCount(index, 0, 0);
        int day = minTime / t;
        if(minTime % t > 0){
            day++;
        }
        return day;
    }

    static void findMaxSolveCount(int current, int depth, int time) {
        Node node = nodes[current];
        isVisited[current] = true;
        if(isChangeCondition(depth, time)){
            index = current;
            maxCount = depth;
            minTime = time;
        }
        for(Next next : node.link){
            if(isVisited[next.num]) continue;
            findMaxSolveCount(next.num, depth + 1, time + next.time);
        }
    }

    static boolean isChangeCondition(int depth, int time){
        return depth > maxCount || (depth == maxCount && minTime > time);
    }

    static class Node {
        int num;
        List<Next> link = new ArrayList<>();

        Node(int num) {
            this.num = num;
        }
    }

    static class Next{
        int num;
        int time;
        Next(int num, int time){
            this.num = num;
            this.time = time;
        }
    }
}