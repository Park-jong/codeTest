import java.io.*;

public class Main_1594_전화번호만들기 {

    static int[] scoreGroup2;
    static int[] scoreGroup3;
    static String num;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        num = br.readLine();
        n = num.length();
        scoreGroup2 = makeScoreGroupLengthTwo();
        scoreGroup3 = makeScoreGroupLengthThree();
        String result = findMaxScore();
        bw.write(result);
        bw.close();
        br.close();
    }

    static int[] makeScoreGroupLengthTwo() {
        int[] scoreGroup = new int[n];
        for (int i = 0; i < n - 1; i++) {
            scoreGroup[i] = checkScoreLengthTwo(num.charAt(i), num.charAt(i + 1));
        }
        return scoreGroup;
    }

    static int checkScoreLengthTwo(char num1, char num2) {
        if (num1 == num2) {
            return 2;
        }
        return 0;
    }

    static int[] makeScoreGroupLengthThree() {
        int[] scoreGroup = new int[n];
        for (int i = 0; i < n - 2; i++) {
            scoreGroup[i] = checkScoreLengthThree(num.charAt(i), num.charAt(i + 1), num.charAt(i + 2));
        }
        return scoreGroup;
    }

    static int checkScoreLengthThree(char num1, char num2, char num3) {
        boolean check1 = num1 == num2;
        boolean check2 = num2 == num3;
        boolean check3 = num1 == num3;
        if (check1 && check2) {
            return 2;
        }
        if (check1 || check2 || check3) {
            return 1;
        }
        return 0;
    }

    static String findMaxScore() {
        String[] strings = new String[n + 1];
        int[] scores = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            fillMaxScoreCurrentIndex(strings, scores, i);
        }
        return strings[n];
    }

    static void fillMaxScoreCurrentIndex(String[] strings, int[] scores, int index) {
        fillMaxScoreCurrentIndexGroupTwo(strings, scores, index);
        fillMaxScoreCurrentIndexGroupThree(strings, scores, index);

    }

    static void fillMaxScoreCurrentIndexGroupTwo(String[] strings, int[] scores, int index){
        int prevIndex = index - 2;
        if(index == 3){
            return;
        }
        scores[index] = scores[prevIndex] + scoreGroup2[prevIndex];
        if(index == 2){
            strings[index] = num.substring(prevIndex, index);
            return;
        }
        strings[index] = strings[prevIndex] + "-" + num.substring(prevIndex, index);
    }

    static void fillMaxScoreCurrentIndexGroupThree(String[] strings, int[] scores, int index){
        int prevIndex = index - 3;
        if(index < 3 || index == 4){
            return;
        }
        int score = scores[prevIndex] + scoreGroup3[prevIndex];
        if(index == 3){
            scores[index] = score;
            strings[index] = num.substring(prevIndex, index);
            return;
        }
        String string = strings[prevIndex] + "-" + num.substring(prevIndex, index);
        if(score > scores[index]){
            scores[index] = score;
            strings[index] = string;
            return;
        }
        if(score == scores[index]){
            String compareString = strings[index];
            if(compareString.compareTo(string) > 0){
                strings[index] = string;
            }
        }
    }
}