import java.io.*;

public class Main_3779_KMP_주기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int i = 1;
        while (true) {
            int test = Integer.parseInt(br.readLine());
            if (test == 0) break;
            sb.append(String.format("Test case #%d\n%s\n", i++, findPrefix(br.readLine())));
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static String findPrefix(String string) {
        int[] array = findRepeat(string);
        int n = string.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n; i++) {
            int a = array[i];
            int b = i + 1 - a;
            if (a < b || a % b != 0) continue;
            sb.append(i + 1).append(" ").append((i + 1) / b).append("\n");
        }
        return sb.toString();
    }

    static int[] findRepeat(String string) {
        int n = string.length();
        int[] array = new int[n];
        int j = 0;
        for (int i = 1; i < n; i++) {
            while (j > 0 && string.charAt(i) != string.charAt(j)) {
                j = array[j - 1];
            }
            if (string.charAt(i) == string.charAt(j)) {
                array[i] = ++j;
            }
        }
        return array;
    }
}