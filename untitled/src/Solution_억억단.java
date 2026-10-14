class Solution_억억단 {
    public int[] solution(int e, int[] starts) {
        int[] count = new int[e + 1];
        for(int i = 1; i <= e; i++){
            for(int j = 1; j <= e / i; j++){
                count[i * j]++;
            }
        }
        int[] num = new int[e + 1];
        num[e] = e;
        for(int i = e - 1; i > 0 ; i--){
            num[i] = count[i] >= count[num[i + 1]] ? i : num[i + 1];
        }
        int[] answer = new int[starts.length];
        for(int i = 0; i < starts.length; i++){
            answer[i] = num[starts[i]];
        }
        return answer;
    }
}