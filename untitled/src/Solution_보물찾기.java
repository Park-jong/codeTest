import java.util.function.Function;
import java.util.*;

class Solution_보물찾기 {
    public int solution(int[] depth, int money, Function<Integer, Integer> excavate) {
        int w = depth.length;
        int[][] dp = new int[w][w];
        int[][] choice = new int[w][w];
        for(int i = 0; i < w; i++){
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][i] = depth[i];
            choice[i][i] = i;
        }
        for(int size = 1; size < w; size++){
            for(int left = 0, right = left + size; left < w && right < w; left++, right++){
                for(int point = left; point <= right; point++){
                    int cost = depth[point]
                            + Math.max(left < point ? dp[left][point - 1] : 0
                            , point < right ? dp[point + 1][right] : 0);
                    if(cost < dp[left][right]){
                        dp[left][right] = cost;
                        choice[left][right] = point;
                    }
                }
            }
        }
        int result = 0;
        int l = 0;
        int r = w - 1;
        while(l <= r){
            int position = choice[l][r];
            result = excavate.apply(position + 1);
            if(result == 0){
                return position + 1;
            } else if(result == -1){
                r = position - 1;
            } else{
                l = position + 1;
            }
        }
        return 0;
    }

}