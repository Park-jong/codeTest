import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main_20210_파일탐색기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = br.readLine();
        }
        String result = sort(n, strings);
        bw.write(result);
        bw.close();
        br.close();
    }

    static String sort(int n, String[] strings) {
        String2[] string2s = new String2[n];
        for (int i = 0; i < n; i++) {
            string2s[i] = new String2(strings[i]);
        }
        Arrays.sort(string2s, String2::compareTo);
        int i = string2s[0].compareTo(string2s[1]);
        StringBuilder sb = new StringBuilder();
        for (String2 s2 : string2s) {
            sb.append(s2.string).append("\n");
        }
        return sb.toString();
    }

    static class String2 implements Comparable<String2> {
        String string;
        List<String> strings = new ArrayList<>();

        String2(String string) {
            this.string = string;
            int n = string.length();
            if (n == 0) return;
            boolean isDigit = Character.isDigit(string.charAt(0));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (isDigit != Character.isDigit(string.charAt(i))) {
                    strings.add(sb.toString());
                    sb = new StringBuilder();
                    isDigit = !isDigit;
                }
                sb.append(string.charAt(i));
            }
            strings.add(sb.toString());
        }

        @Override
        public int compareTo(String2 b) {
            int n = this.strings.size();
            int m = b.strings.size();
            for (int i = 0; i < n && i < m; i++) {
                boolean aIsDigit = Character.isDigit(this.strings.get(i).charAt(0));
                boolean bIsDigit = Character.isDigit(b.strings.get(i).charAt(0));
                String aStr = this.strings.get(i);
                String bStr = b.strings.get(i);
                if (aIsDigit && bIsDigit) {
                    BigInteger A = new BigInteger(aStr);
                    BigInteger B = new BigInteger(bStr);
                    if (!A.equals(B)) {
                        return A.compareTo(B);
                    } else if (aStr.length() != bStr.length()) {
                        return Integer.compare(aStr.length(), bStr.length());
                    }
                } else if (!aIsDigit && !bIsDigit) {
                    if (aStr.equals(bStr)) continue;
                    int aLength = aStr.length();
                    int bLength = bStr.length();
                    for (int j = 0; j < aLength && j < bLength; j++) {
                        if (aStr.charAt(j) == bStr.charAt(j)) continue;
                        if (Character.toLowerCase(aStr.charAt(j)) == Character.toLowerCase(bStr.charAt(j))) {
                            return Character.isLowerCase(aStr.charAt(j)) ? 1 : -1;
                        }
                        return Character.compare(Character.toLowerCase(aStr.charAt(j)), Character.toLowerCase(bStr.charAt(j)));
                    }
                    return Integer.compare(aLength, bLength);
                } else {
                    break;
                }
            }
            return this.string.compareTo(b.string);
        }
    }
}