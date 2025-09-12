import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_15892_사탕줍는로봇 {

    static int n;
    static int m;
    static int[][] flow;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        flow = new int[n][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            flow[a - 1][b - 1] += c;
            flow[b - 1][a - 1] += c;
        }
        int result=  findMaxValue();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxValue() {
        int result = 0;
        int start = 0;
        int goal = n - 1;
        while(true) {
            int[] parent = findPath();
            if(parent[goal] == -1){
                break;
            }
            int min = Integer.MAX_VALUE;
            for(int i = goal; i != start; i = parent[i]){
                min = Math.min(min, flow[parent[i]][i]);
            }
            for(int i = goal; i != start; i = parent[i]){
                flow[parent[i]][i] -= min;
                flow[i][parent[i]] += min;
            }
            result += min;
        }
        return result;
    }

    static int[] findPath() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        while(!queue.isEmpty()){
            int current = queue.poll();
            for(int i = 0; i < n; i++){
                if(flow[current][i] > 0 && parent[i] == -1 && i != 0){
                    parent[i] = current;
                    queue.offer(i);
                }
            }
            if(parent[n - 1] != -1){
                break;
            }
        }
        return parent;
    }
}