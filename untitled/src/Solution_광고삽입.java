class Solution_광고삽입 {
    public String solution(String play_time, String adv_time, String[] logs) {
        int n = logs.length;
        int playTime = stringToInt(play_time);
        int advTime = stringToInt(adv_time);
        Log[] log = new Log[n];
        for (int i = 0; i < n; i++) {
            log[i] = new Log(logs[i]);
        }
        String answer = findAdvPosition(playTime, advTime, log);
        return answer;
    }

    int stringToInt(String time) {
        int h = Integer.parseInt(time.substring(0, 2));
        int m = Integer.parseInt(time.substring(3, 5));
        int s = Integer.parseInt(time.substring(6, 8));
        return h * 60 * 60 + m * 60 + s;
    }

    String findAdvPosition(int playTime, int advTime, Log[] logs) {
        int[] count = new int[playTime + 1];
        for (Log log : logs) {
            count[log.start] += 1;
            count[log.end] -= 1;
        }
        for (int i = 1; i <= playTime; i++) {
            count[i] = count[i - 1] + count[i];
        }
        long sum = 0;
        for (int i = 0; i < advTime; i++) {
            sum += count[i];
        }
        int start = 0;
        long max = sum;
        for (int i = 1; i + advTime - 1 <= playTime; i++) {
            sum = sum - count[i - 1] + count[i + advTime - 1];
            if (sum > max) {
                max = sum;
                start = i;
            }
        }
        return intToString(start);
    }

    String intToString(int time) {
        int h = time / (60 * 60);
        int m = (time % (60 * 60)) / 60;
        int s = time % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    class Log {
        int start;
        int end;

        Log(String log) {
            String[] s = log.split("-");
            start = stringToInt(s[0]);
            end = stringToInt(s[1]);
        }
    }
}