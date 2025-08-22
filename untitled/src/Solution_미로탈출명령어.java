class Solution_미로탈출명령어 {
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        String answer = "";
        int[] dx = {1, 0, 0, -1};
        int[] dy = {0, -1, 1, 0};
        char[] d = {'d', 'l', 'r', 'u'};
        char[] answerChar = new char[k];
        boolean possible = isPossible(x, y, r, c, k);
        if(!possible){
            return "impossible";
        }
        for(int i = 0; i < k; i++){
            for(int j = 0; j < 4; j++){
                int nextX = x + dx[j];
                int nextY = y + dy[j];
                if(nextX <= 0 || nextX > n || nextY <= 0 || nextY > m) continue;
                if(!isInDistance(nextX, nextY, r, c, k - i - 1)) continue;
                answerChar[i] = d[j];
                x = nextX;
                y = nextY;
                break;
            }
        }
        answer = String.valueOf(answerChar);
        return answer;
    }

    boolean isPossible(int x, int y, int r, int c, int k){
        int distance = distance(x, y, r, c);
        return isInDistance(x, y, r, c, k) && distance % 2 == k % 2;
    }

    boolean isInDistance(int x, int y, int r, int c, int k){
        return distance(x, y, r, c) <= k;
    }

    int distance(int x, int y, int r, int c){
        return Math.abs(x - r) + Math.abs(y - c);
    }
}