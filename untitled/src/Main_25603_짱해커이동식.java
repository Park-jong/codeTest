import java.io.*;
import java.util.StringTokenizer;

public class Main_25603_짱해커이동식 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] works = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            works[i] = Integer.parseInt(st.nextToken());
        }
        int result = binarySearch(works, k);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int binarySearch(int[] works, int k){
        int n = works.length;
        int left = 0;
        int right = Integer.MAX_VALUE;
        int result = 0;
        while(left <= right){
            int mid = (left + right) >> 1;
            int count = 0;
            boolean possible = true;
            for(int i = 0; i < k; i++){
                if(works[i] > mid){
                    count++;
                }
            }
            if(count == k){
                possible = false;
            }
            if(possible) {
                for (int i = 0; i < n - k; i++) {
                    if (works[i] > mid) {
                        count--;
                    }
                    if (works[i + k] > mid) {
                        count++;
                    }
                    if (count == k) {
                        possible = false;
                        break;
                    }
                }
            }
            if(possible){
                result = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return result;
    }
}