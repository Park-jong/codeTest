import java.io.*;
import java.util.StringTokenizer;

public class Main_12907_동물원 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] size = new int[41];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int s = Integer.parseInt(st.nextToken());
            size[s]++;
        }
        int result = numberOfCases(size);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int numberOfCases(int[] size) {
        int first = setFirst(size[0]);
        return updateAllCases(size, first);
    }

    static int setFirst(int current) {
        if (existOneAnimalType(current) || existTwoAnimalTypes(current)) {
            return 2;
        }
        return 0;
    }

    static boolean existOneAnimalType(int current) {
        return current == 1;
    }

    static boolean existTwoAnimalTypes(int current) {
        return current == 2;
    }

    static int updateAllCases(int[] size, int result){
        for (int i = 1; i <= 40; i++) {
            result = updateNumberOfCase(size[i - 1], size[i], result);
        }
        return result;
    }

    static int updateNumberOfCase(int prev, int current, int result) {
        if (prev < current){
            return 0;
        }
        if(existTwoAnimalTypes(current)){
            return result * 2;
        }
        if(existTwoAnimalTypes(prev) && existOneAnimalType(current)){
            return result * 2;
        }
        return result;
    }
}