import java.io.*;

public class Main_18109_도깨비불 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String str = br.readLine();
        int n = str.length();
        int[] types = new int[n];
        for (int i = 0; i < n; i++) {
            types[i] = parseType(str.charAt(i));
        }
        int count = 0;
        for (int i = 0; i < n - 2; i++) {
            if (check3words(types[i], types[i + 1], types[i + 2])) {
                count++;
            }
        }
        for (int i = 0; i < n - 3; i++) {
            if (check4words(types[i], str.charAt(i + 1), str.charAt(i + 2), types[i + 3])) {
                count++;
            }
        }
        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }

    static int parseType(char c) {
        switch (c) {
            case 'r':
            case 'R':
            case 's':
            case 'e':
            case 'f':
            case 'a':
            case 'q':
            case 't':
            case 'T':
            case 'd':
            case 'w':
            case 'c':
            case 'z':
            case 'x':
            case 'v':
            case 'g':
                return 1;
            case 'k':
            case 'o':
            case 'i':
            case 'O':
            case 'j':
            case 'p':
            case 'u':
            case 'P':
            case 'h':
            case 'y':
            case 'n':
            case 'b':
            case 'm':
            case 'l':
                return 2;
            default:
                return 0;

        }
    }

    static boolean check3words(int t1, int t2, int t3) {
        return t1 == 2 && t2 == 1 && t3 == 2;
    }

    static boolean check4words(int t1, char c2, char c3, int t4) {
        String check = new String(new char[]{c2, c3});
        boolean possible = false;
        switch (check) {
            case "rt":
            case "sw":
            case "sg":
            case "fr":
            case "fa":
            case "fq":
            case "ft":
            case "fx":
            case "fv":
            case "fg":
            case "qt":
                possible = true;
                break;
            default:
                break;
        }
        return t1 == 2 && possible && t4 == 2;
    }
}