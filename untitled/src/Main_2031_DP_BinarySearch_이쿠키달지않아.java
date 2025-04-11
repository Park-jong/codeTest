import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2031_DP_BinarySearch_이쿠키달지않아 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] food = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            food[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(food);
        Effect[] effects = new Effect[n];
        for (int i = 0; i < n; i++) {
            int next = BinarySearchNext(food, i, d);
            int prev = BinarySearchPrev(food, i, d);
            effects[i] = new Effect(food[i], i, next, prev, next - i);
        }

        int reuslt = Dynamic(n, k, effects);

        bw.write(String.valueOf(reuslt));
        bw.flush();
        bw.close();
        br.close();
    }

    static int Dynamic(int n, int k, Effect[] effects){
        int[][] dp = new int[k + 1][n];

        for(int i = 1; i <= k; i++){
            for(int j = 0; j < n; j++){
                Effect current = effects[j];
                int prev = current.prevIndex;
                int count = current.count;
                dp[i][j] = Math.max(dp[i-1][j], count);
                if(current.prevIndex >= 0){
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][prev] + count);
                }
                if(j > 0){
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                }
            }
        }
        return dp[k][n-1];
    }

    static int BinarySearchNext(int[] food, int index, int d){
        int start = food[index];
        int left = index;
        int right = food.length - 1;
        int next = right + 1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(food[mid] >= start + d){
                next = mid;
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }
        return next;
    }

    static int BinarySearchPrev(int[] food, int index, int d){
        int start = food[index];
        int left = 0;
        int right = index;
        int prev = -1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(food[mid] <= start - d){
                prev = mid;
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }
        return prev;
    }

    private static class Effect {
        int start;
        int count;
        int index;
        int nextIndex;
        int prevIndex;

        Effect( int start, int index, int nextIndex, int prevIndex, int count) {
            this.index = index;
            this.start = start;
            this.nextIndex = nextIndex;
            this.prevIndex = prevIndex;
            this.count = count;
        }
    }
}