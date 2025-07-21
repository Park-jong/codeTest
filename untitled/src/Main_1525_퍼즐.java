import java.io.*;
import java.util.*;

public class Main_1525_퍼즐 {

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static Set<String> used = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] start = new int[9];
        for (int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                start[index] = Integer.parseInt(st.nextToken());
            }
        }
        int result = findMinMoveCount(arrayToString(start));
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        return sb.toString();
    }

    static int findMinMoveCount(String start) {
        if (isGoal(start)) {
            return 0;
        }
        used.add(start);
        Queue<Puzzle> queue = new ArrayDeque<>();
        queue.offer(new Puzzle(start, 0));
        while (!queue.isEmpty()) {
            Puzzle current = queue.poll();
            int zeroIndex = findZeroIndex(current.string);
            int x = zeroIndex / 3;
            int y = zeroIndex % 3;
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                Puzzle puzzle = movePuzzle(current, newX, newY);
                if (puzzle == null || used.contains(puzzle.string)) {
                    continue;
                }
                if (isGoal(puzzle.string)) {
                    return puzzle.move;
                }
                used.add(puzzle.string);
                queue.offer(puzzle);
            }

        }
        return -1;
    }

    static boolean isGoal(String string) {
        return string.equals("123456780");
    }

    static int findZeroIndex(String string) {
        return string.indexOf('0');
    }

    static Puzzle movePuzzle(Puzzle prev, int newX, int newY) {
        int move = prev.move + 1;
        if (newX >= 0 && newY >= 0 && newX < 3 && newY < 3) {
            int moveZero = newX * 3 + newY;
            String newString = swap(prev.string, findZeroIndex(prev.string), moveZero);
            return new Puzzle(newString, move);
        }
        return null;
    }

    static String swap(String s, int i, int j) {
        char[] arr = s.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }

    static class Puzzle{
        String string;
        int move;
        Puzzle(String string, int move){
            this.string = string;
            this.move = move;
        }
    }
}