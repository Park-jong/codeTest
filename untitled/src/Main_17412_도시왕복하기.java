import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17412_도시왕복하기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int[][] road = new int[n + 1][n + 1];
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            road[a][b] += 1;
        }
        int result = findMaxValue(n, road);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxValue(int n, int[][] road){
        int result = 0;
        while(true){
            int[] parent = findPath(n, road);
            if(parent[2] == -1){
                break;
            }
            int min = Integer.MAX_VALUE;
            for(int i = 2; i != 1; i = parent[i]){
                min = Math.min(min, road[parent[i]][i]);
            }
            for(int i = 2; i != 1; i = parent[i]){
                road[parent[i]][i] -= min;
                road[i][parent[i]] += min;
            }
            result += min;
        }
        return result;
    }

    static int[] findPath(int n, int[][] road){
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        int[] parent = new int[n + 1];
        Arrays.fill(parent, -1);
        parent[1] = 0;
        while(!queue.isEmpty()){
            int current = queue.poll();
            for(int i = 1; i <= n; i++){
                if(road[current][i] > 0 && parent[i] == -1){
                    parent[i] = current;
                    queue.offer(i);
                }
            }
            if(parent[2] != -1){
                break;
            }
        }
        return parent;
    }
}