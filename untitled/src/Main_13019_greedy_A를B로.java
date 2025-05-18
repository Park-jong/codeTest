import java.io.*;

public class Main_13019_greedy_A를B로 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String A = br.readLine();
        String B = br.readLine();

        int[] spellA = new int[26];
        int[] spellB = new int[26];
        int length = A.length();
        for (int i = 0; i < length; i++) {
            spellA[A.charAt(i) - 'A']++;
            spellB[B.charAt(i) - 'A']++;
        }
        int result = 0;
        for (int i = 0; i < 26; i++) {
            if (spellA[i] != spellB[i]) result = -1;
        }
        if (result != -1) {
            result = compareString(A, B);
        }

        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int compareString(String A, String B) {
        int pointerA = A.length() - 1;
        int pointerB = pointerA;
        int count = 0;
        while (pointerA >= 0) {
            if (A.charAt(pointerA) == B.charAt(pointerB)) {
                pointerA--;
                pointerB--;
            } else {
                count++;
                pointerA--;
            }
        }
        return count;
    }
}