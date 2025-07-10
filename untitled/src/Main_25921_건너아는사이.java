import java.io.*;

public class Main_25921_건너아는사이 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        long result = getResult(n);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }
    static long getResult(int n){
        boolean[] array = new boolean[n + 1];
        long result = 0;
        for(int i = 2; i <= n; i++){
            result += filter(array, n, i);
        }
        return result;
    }
    static long filter(boolean[] array, int n, int index){
        if(array[index]){
            return 0;
        }
        long sumGCD = 0;
        for(int i = index; i <= n; i += index){
            if(!array[i]){
                sumGCD += index;
                array[i] = true;
            }
        }
        return sumGCD;
    }
}