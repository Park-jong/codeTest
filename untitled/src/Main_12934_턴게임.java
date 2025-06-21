import java.io.*;
import java.util.StringTokenizer;

public class Main_12934_턴게임 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long x = Long.parseLong(st.nextToken());
        long y = Long.parseLong(st.nextToken());
        long lastNum = getLastNumber(x + y);
        long result = getFinalCount(lastNum, x);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long getLastNumber(long num) {
        if (num == 0L) return 0L;
        long mul = num * 2;
        long sqrt = (long) Math.sqrt(mul);
        if (sqrt * (sqrt + 1) == mul) {
            return sqrt;
        }
        return -1;
    }

    static long getFinalCount(long lastNum, long x){
        long count = 0;
        long goal = x;
        long result = -1;
        if(lastNum != -1){
            while(goal > 0){
                Result binarySearchResult = binarySearchCount(goal, lastNum);
                count += binarySearchResult.count;
                goal = binarySearchResult.remain;
            }
            result = count;
        }
        return result;
    }

    static Result binarySearchCount(long goal, long lastNum) {
        if (lastNum >= goal) {
            return new Result(1, 0);
        }
        long left = 1;
        long right = lastNum;
        long count = 0;
        long remain = 0;
        while (left <= right) {
            long mid = (left + right) / 2;
            long sum = sequenceSum(mid, lastNum);
            if (sum <= goal) {
                count = lastNum - mid + 1;
                remain = goal - sum;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return new Result(count, remain);
    }

    static long sequenceSum(long left, long right) {
        return (left + right) * (right - left + 1) / 2;
    }

    static class Result {
        long count;
        long remain;

        Result(long count, long remain) {
            this.count = count;
            this.remain = remain;
        }
    }
}