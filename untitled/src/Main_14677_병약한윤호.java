import java.io.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Main_14677_병약한윤호 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String str = br.readLine();
        int result = findMaxValue(n, str);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findMaxValue(int n, String str) {
        int m = n * 3;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(str);
        Set<String> set = new HashSet<>();
        int result = 0;
        while (!queue.isEmpty()) {
            String current = queue.poll();
            result = Math.max(m - current.length(), result);
            int type = (m - current.length()) % 3;
            if (type == 0) {
                checkAndAddQueue(queue, set, current, 'B');
            } else if (type == 1) {
                checkAndAddQueue(queue, set, current, 'L');
            } else {
                checkAndAddQueue(queue, set, current, 'D');
            }
        }
        return result;
    }

    static void checkAndAddQueue(Queue<String> queue, Set<String> set, String str, char type) {
        if(str.isEmpty()) return;
        addQueue(queue, set, removeFirst(str, type));
        addQueue(queue, set, removeLast(str, type));
    }

    static String removeFirst(String str, char type) {
        if (str.charAt(0) == type) {
            return str.substring(1);
        }
        return null;
    }

    static String removeLast(String str, char type) {
        if (str.charAt(str.length() - 1) == type) {
            return str.substring(0, str.length() - 1);
        }
        return null;
    }

    static void addQueue(Queue<String> queue, Set<String> set, String str) {
        if (str != null && !set.contains(str)) {
            set.add(str);
            queue.offer(str);
        }
    }
}