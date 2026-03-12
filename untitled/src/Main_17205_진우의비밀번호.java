import java.io.*;

public class Main_17205_진우의비밀번호 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String password = br.readLine();
        long result = findTime(n, password);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static long findTime(int n, String password) {
        int length = password.length();
        long[] count = new long[n+1];
        for (int i = 1; i <= n; i++) {
            count[i] = (count[i - 1] + 1) * 26;
        }
        long result = 0;
        for (int i = 0; i < length; i++) {
            result += (count[n - i - 1] + 1) * (password.charAt(i) - 'a');
            result++;
        }
        return result;
    }
}