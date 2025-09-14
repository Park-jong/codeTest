import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_10976_피난 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int i = 0; i < t; i++){
            sb.append(solve(br)).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int solve(BufferedReader br) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] road = new int [n + 1][n + 1];
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            road[Math.min(a, b)][Math.max(a, b)] = 1;
        }
        return findMaxValue(road, n);
    }

    static int findMaxValue(int[][] road, int n){
        int result = 0;
        while(true){
            int[] parent = findPath(road, n);
            if(parent[n] == -1){
                break;
            }
            int min = Integer.MAX_VALUE;
            for(int i = n; i != 1; i = parent[i]){
                min = Math.min(min, road[parent[i]][i]);
            }
            for(int i = n; i != 1; i = parent[i]){
                road[parent[i]][i] -= min;
                road[i][parent[i]] += min;
            }
            result += min;
        }
        return result;
    }

    static int[] findPath(int[][] road, int n){
        int start = 1;
        int[] parent = new int[n + 1];
        Arrays.fill(parent, -1);
        parent[1] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        while(!queue.isEmpty()){
            int current = queue.poll();
            for(int i = 1; i <= n; i++){
                if(road[current][i] > 0 && parent[i] == -1){
                    queue.offer(i);
                    parent[i] = current;
                }
            }
            if(parent[n] != -1){
                break;
            }
        }
        return parent;
    }
}