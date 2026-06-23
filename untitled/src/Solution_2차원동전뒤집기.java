class Solution_2차원동전뒤집기 {
    public int solution(int[][] beginning, int[][] target) {
        int row = beginning.length;
        int col = beginning[0].length;
        int pattern = 0;
        int xor = (1 << col) - 1;
        int rCount = 0;
        int cCount = 0;
        for(int j = 0; j < col; j++){
            if(beginning[0][j] != target[0][j]){
                pattern |= 1 << j;
                cCount++;
            }
        }
        for(int i = 1; i < row; i++){
            int bit = 0;
            for(int j = 0; j < col; j++){
                if(beginning[i][j] != target[i][j]){
                    bit |= 1 << j;
                }
            }
            if(bit != pattern && (bit ^ pattern) != xor){
                return -1;
            }
            if((bit ^ pattern) == xor){
                rCount++;
            }
        }
        int answer = Math.min(rCount + cCount, row - rCount + col - cCount);
        return answer;
    }
}