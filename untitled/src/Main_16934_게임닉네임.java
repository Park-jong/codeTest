import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main_16934_게임닉네임 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Map<String, Integer> count = new HashMap<>();
        Prefix root = new Prefix();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            String prefix;
            if (!count.containsKey(str)) {
                prefix = root.makePrefix(str, 0);
                count.put(str, 1);
            } else {
                int c = count.get(str) + 1;
                count.put(str, c);
                prefix = str + c;
            }
            sb.append(prefix).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static class Prefix {
        String prefix;
        Map<Character, Prefix> next = new HashMap<>();

        String makePrefix(String str, int index) {
            if (str.length() <= index) {
                return str;
            }
            String prefix = null;
            char c = str.charAt(index);
            if (!next.containsKey(c)) {
                Prefix newPrefix = new Prefix();
                next.put(c, newPrefix);
                prefix = newPrefix.prefix = str.substring(0, index + 1);
            }
            Prefix nextChar = next.get(c);
            String nextPrefix = nextChar.makePrefix(str, index + 1);
            if (prefix == null) {
                prefix = nextPrefix;
            }
            return prefix;
        }
    }
}