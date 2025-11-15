import java.io.*;

public class Main_1701_KMP_Cubeditor {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String string = br.readLine();
        int result = findLongest(string);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findLongest(String string) {
        int n = string.length();
        int result = 0;
        for(int i = 0; i < n; i++){
            result = Math.max(findSubString(string.substring(i)), result);
        }
        return result;
    }
    static int findSubString(String string){
        int n = string.length();
        int[] array = new int[n];
        int result = 0;
        int j = 0;
        for (int i = 1; i < n; i++) {
            while (string.charAt(i) != string.charAt(j) && j > 0) {
                j = array[j - 1];
            }
            if (string.charAt(i) == string.charAt(j)) {
                array[i] = ++j;
                result = Math.max(result, array[i]);
            }
        }
        return result;
    }
}