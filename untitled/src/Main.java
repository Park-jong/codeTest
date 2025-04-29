import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String S = br.readLine();
        String T = br.readLine();

        int sLength = S.length();
        int tLength = T.length();
        Queue<String> q = new ArrayDeque<>();
        q.offer(T);
        int result = 0;
        while (!q.isEmpty()) {
            String current = q.poll();
            String changeString = removeLastChar(current);

            if (changeString != null && !changeString.isEmpty()) {
                if (changeString.equals(S)) {
                    result = 1;
                    break;
                }
                q.offer(changeString);

            }
            changeString = reverseAndRemoveLastChar(current);
            if (changeString != null && !changeString.isEmpty()) {
                if (changeString.equals(S)) {
                    result = 1;
                    break;
                }
                q.offer(changeString);
            }

        }
        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static String removeLastChar(String T) {
        if (T.charAt(T.length() - 1) == 'B') return null;
        return T.substring(0, T.length() - 1);
    }

    static String reverseAndRemoveLastChar(String T) {
        if (T.charAt(0) == 'A') return null;
        StringBuilder sb = new StringBuilder(T);
        return sb.reverse().substring(0, T.length() - 1);
    }
}