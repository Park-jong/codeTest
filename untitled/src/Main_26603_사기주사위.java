import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_26603_사기주사위 {
    static int[][] choice = new int[8][4];
    static int[][] check = new int[9][9];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int count = 0;
        boolean result = true;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] numbers = new int[4];
            for (int j = 0; j < 4; j++) {
                numbers[j] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(numbers);
            if (result) {
                count = compareAndAddArray(numbers, count);
            }
            if (count < 0) {
                result = false;
            }
        }
        String resultString = getResult(result);
        bw.write(resultString);
        bw.close();
        br.close();
    }

    static int compareAndAddArray(int[] array, int count) {
        for (int i = 0; i < count; i++) {
            int sameCount = compareArray(choice[i], array);
            if (sameCount == 1 || sameCount == 3) return -1;
            else if (sameCount == 4) return count;
        }
        if (count == 8) {
            return -1;
        }
        if (!addArray(array, count)) {
            return -1;
        }
        return count + 2;
    }

    static boolean addArray(int[] array, int count) {
        int[] otherArray = new int[4];
        int index1 = 0;
        int index2 = 0;
        for (int i = 1; i < 9; i++) {
            if (index1 < 4 && array[index1] == i) {
                index1++;
            } else {
                otherArray[index2++] = i;
            }
        }
        choice[count] = array;
        choice[count + 1] = otherArray;
        return checkPossible(array) && checkPossible(otherArray);
    }

    static int compareArray(int[] array1, int[] array2) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (array1[i] == array2[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    static boolean checkPossible(int[] array) {
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (++check[Math.min(array[i], array[j])][Math.max(array[i], array[j])] > 2)
                    return false;
            }
        }
        return true;
    }

    static String getResult(boolean condition) {
        return condition ? "Hmm..." : "You're gonna die!";
    }
}