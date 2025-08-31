import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2831_댄스파티 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] men = new int[n];
        int[] women = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            men[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            women[i] = Integer.parseInt(st.nextToken());
        }
        int result = match(n, men, women);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int match(int n, int[] men, int[] women) {
        Arrays.sort(men);
        Arrays.sort(women);
        int count = 0;
        int i = 0;
        int j = n - 1;
        while (i < n && j >= 0) {
            if (men[i] < 0) {
                if (-men[i] <= women[j]) {
                    j--;
                    continue;
                }
                if (women[j] < 0) {
                    i++;
                    continue;
                }
                count++;

            } else {
                if (women[j] > 0 || -women[j] <= men[i]){
                    j--;
                    continue;
                }
                count++;
            }
            i++;
            j--;
        }
        return count;
    }
}