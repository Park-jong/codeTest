import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_5972_다익스트라_택배배송 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<Edge>[] edges = new List[n];
        for(int i = 0; i < n; i++){
            edges[i] = new ArrayList<Edge>();
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int point1 = Integer.parseInt(st.nextToken()) - 1;
            int point2 = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            edges[point1].add(new Edge(point1, point2, d));
            edges[point2].add(new Edge(point2, point1, d));
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        for(Edge e : edges[0]){
            pq.offer(new int[]{e.point2, e.distance});
        }
        boolean[] isVisited = new boolean[n];
        isVisited[0] = true;
        int result = 0;
        while(!pq.isEmpty()){
            int[] current = pq.poll();
            int node = current[0];
            int dist = current[1];
            if(isVisited[node]) continue;
            isVisited[node] = true;
            if(node == n - 1) {
                result = dist;
                break;
            }
            for(Edge e : edges[node]){
                if(isVisited[e.point2]) continue;
                pq.offer(new int[]{e.point2, dist + e.distance});
            }
        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }
    static class Edge {
        int point1;
        int point2;
        int distance;
        Edge(int point1, int point2, int distance){
            this.point1 = point1;
            this.point2 = point2;
            this.distance = distance;
        }
    }
}