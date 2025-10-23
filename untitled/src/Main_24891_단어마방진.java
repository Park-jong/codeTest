import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_24891_단어마방진 {

    static char[][] magicSquare;
    static int length;
    static int n;
    static String[] words;
    static boolean[] isUsed;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        length = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = br.readLine();
        }
        String result = findMagicSquare();
        bw.write(result);
        bw.close();
        br.close();
    }

    static String findMagicSquare() {
        magicSquare = new char[n][n];
        isUsed = new boolean[n];
        StringBuilder sb = new StringBuilder();
        Arrays.sort(words, String::compareTo);
        if (makeMagicSquare(0)) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    sb.append(magicSquare[i][j]);
                }
                sb.append("\n");
            }
        } else {
            sb.append("NONE");
        }
        return sb.toString();
    }

    static boolean makeMagicSquare(int depth) {
        if (depth == length) {
            return true;
        }
        word:
        for (int i = 0; i < n; i++) {
            if (isUsed[i]) continue;
            for (int j = 0; j < depth; j++) {
                if (magicSquare[j][depth] != words[i].charAt(j)) {
                    continue word;
                }
                magicSquare[depth][j] = words[i].charAt(j);
            }
            for (int j = depth; j < length; j++) {
                magicSquare[depth][j] = words[i].charAt(j);
            }
            isUsed[i] = true;
            if (makeMagicSquare(depth + 1)) {
                return true;
            }
            isUsed[i] = false;
        }
        return false;
    }
}