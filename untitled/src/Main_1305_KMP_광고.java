import java.io.*;

public class Main_1305_KMP_광고 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        String str = br.readLine();
        int result = stringLength(n, str);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int stringLength(int n, String str) {
        int i = 0;
        int[] arr = new int[n];
        for (int j = 1; j < n; j++) {
            while (i != 0 && str.charAt(i) != str.charAt(j)) {
                i = arr[i - 1];
            }
            if(str.charAt(i) == str.charAt(j)){
                arr[j] = ++i;
            }
        }
        return n - arr[n - 1];
    }
}