class Solution_고고학최고의발견 {
    int n;
    int min = Integer.MAX_VALUE;

    public int solution(int[][] clockHands) {
        n = clockHands.length;
        caseOfLine(clockHands, 0, 0);
        int answer = min;
        return answer;
    }

    void caseOfLine(int[][] clockHands, int depth, int c) {
        if (depth == n) {
            count(clockHands, 1, 0, c);
            return;
        }
        for (int i = 0; i < 4; i++) {
            clockHands[0][depth] = (clockHands[0][depth] + i) % 4;
            clockHands[1][depth] = (clockHands[1][depth] + i) % 4;
            if (depth > 0) clockHands[0][depth - 1] = (clockHands[0][depth - 1] + i) % 4;
            if (depth < n - 1) clockHands[0][depth + 1] = (clockHands[0][depth + 1] + i) % 4;
            caseOfLine(clockHands, depth + 1, c + i);
            clockHands[0][depth] = (clockHands[0][depth] - i + 4) % 4;
            clockHands[1][depth] = (clockHands[1][depth] - i + 4) % 4;
            if (depth > 0) clockHands[0][depth - 1] = (clockHands[0][depth - 1] - i + 4) % 4;
            if (depth < n - 1) clockHands[0][depth + 1] = (clockHands[0][depth + 1] - i + 4) % 4;
        }
    }

    void count(int[][] clockHands, int row, int col, int c) {
        if (row == n) {
            for (int i = 0; i < n; i++) {
                if (clockHands[n - 1][i] != 0) return;
            }
            min = Math.min(c, min);
            return;
        }
        int rotate = (4 - clockHands[row - 1][col]) % 4;
        clockHands[row][col] = (clockHands[row][col] + rotate) % 4;
        clockHands[row - 1][col] = (clockHands[row - 1][col] + rotate) % 4;
        if (row < n - 1)
            clockHands[row + 1][col] = (clockHands[row + 1][col] + rotate) % 4;
        if (col > 0)
            clockHands[row][col - 1] = (clockHands[row][col - 1] + rotate) % 4;
        if (col < n - 1)
            clockHands[row][col + 1] = (clockHands[row][col + 1] + rotate) % 4;
        if (col + 1 == n) {
            count(clockHands, row + 1, 0, c + rotate);
        } else {
            count(clockHands, row, col + 1, c + rotate);
        }
        clockHands[row][col] = (clockHands[row][col] - rotate + 4) % 4;
        clockHands[row - 1][col] = (clockHands[row - 1][col] - rotate + 4) % 4;
        if (row < n - 1)
            clockHands[row + 1][col] = (clockHands[row + 1][col] - rotate + 4) % 4;
        if (col > 0)
            clockHands[row][col - 1] = (clockHands[row][col - 1] - rotate + 4) % 4;
        if (col < n - 1)
            clockHands[row][col + 1] = (clockHands[row][col + 1] - rotate + 4) % 4;
    }
}