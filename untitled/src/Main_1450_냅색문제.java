import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1450_냅색문제 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int[] items = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            items[i] = Integer.parseInt(st.nextToken());
        }
        int n1 = (n / 2) + (n % 2);
        int n2 = n / 2;
        long[] array1 = halfArray(Arrays.copyOfRange(items, 0, n1), n1);
        long[] array2 =  halfArray(Arrays.copyOfRange(items, n1, n), n2);
        Arrays.sort(array1);
        Arrays.sort(array2);
        int end = array1.length;
        int result = 0;
        for(int i = 0; i < end; i++){
            if(array1[i] > c)
                break;
            result += binarySearch(array2, c - array1[i]);
        }
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int pow(int n, int count) {
        int num = 1;
        for (int i = 0; i < count; i++) {
            num *= n;
        }
        return num;
    }

    static long[] halfArray(int[] items, int n){
        long[] array = new long[pow(2, n)];
        int size = 1;
        for (int i = 0; i < n; i++) {
            int end = size;
            for (int j = 0; j < end; j++) {
                array[size++] = array[j] + items[i];
            }
        }
        return array;
    }

    static int binarySearch(long[] arr, long find){
        int left = 0;
        int right = arr.length - 1;
        int result = -1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(arr[mid] <= find){
                result = mid;
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }
        return result + 1;
    }
}