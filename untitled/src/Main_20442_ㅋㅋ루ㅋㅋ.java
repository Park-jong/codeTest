import java.io.*;

public class Main_20442_ㅋㅋ루ㅋㅋ {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String str = br.readLine();
        int result = countKKRKKLength(str);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int countKKRKKLength(String str) {
        int n = str.length();
        int countR = 0;
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) == 'R') {
                countR++;
            }
        }
        if (countR == 0) return 0;
        int maxLength = countR;
        int countK = 0;
        int i = 0;
        int j = n - 1;
        while (i < j) {
            if (str.charAt(i) == 'K' && str.charAt(j) == 'K') {
                i++;
                j--;
                countK += 2;
                if (countR > 0) {
                    maxLength = Math.max(maxLength, countK + countR);
                }
            } else if (str.charAt(i) == 'K') {
                j--;
                countR--;
            } else if (str.charAt(j) == 'K') {
                i++;
                countR--;
            } else {
                i++;
                j--;
                countR -= 2;
            }
        }
        return maxLength;
    }
}