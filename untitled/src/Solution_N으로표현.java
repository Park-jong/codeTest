import java.util.*;

class Solution_N으로표현 {
    int[] array;
    int limit;
    List<Integer> list = new ArrayList<>();

    public int solution(int N, int number) {
        limit = N * number;
        array = new int[limit + 1];
        Arrays.fill(array, Integer.MAX_VALUE);
        int count = 1;
        int num = N;
        while (true) {
            if (num > limit) break;
            list.add(num);
            num = num * 10 + N;
            count++;
        }
        for (int i = 0; i < list.size(); i++) {
            findNum(N, list.get(i), i + 1);
        }
        int answer = array[number] != Integer.MAX_VALUE ? array[number] : -1;
        return answer;
    }

    void findNum(int N, int num, int depth) {
        if (depth > 9 || num > limit || num <= 0 || array[num] <= depth)
            return;
        array[num] = depth;
        for (int i = 0; i < list.size(); i++) {
            int n = list.get(i);
            findNum(N, num + n, depth + i + 1);
            findNum(N, num - n, depth + i + 1);
            findNum(N, n - num, depth + i + 1);
            findNum(N, num * n, depth + i + 1);
            findNum(N, num / n, depth + i + 1);
            findNum(N, n / num, depth + i + 1);
        }
    }
}