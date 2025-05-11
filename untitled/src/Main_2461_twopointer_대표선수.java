import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2461_twopointer_대표선수 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        Student[] students = new Student[n * m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                students[i * m + j] = new Student(i, Integer.parseInt(st.nextToken()));
            }
        }
        Arrays.sort(students, (a, b) -> Integer.compare(a.ability, b.ability));
        int joinClass = 0;
        int[] joinStudent = new int[n];
        int left = -1;
        int right = -1;
        int min = Integer.MAX_VALUE;
        while(right != n * m - 1 || joinClass == n){
            if(isExecutable(joinClass, n)){
                Student student = students[++left];
                if(--joinStudent[student.classroom] == 0){
                    joinClass--;
                }
            }else{
                Student student = students[++right];
                if(++joinStudent[student.classroom] == 1){
                    joinClass++;
                }
            }
            if(isExecutable(joinClass, n)){
                min = checkAndChange(students, left, right, min);
            }
        }

        bw.write(String.valueOf(min));
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean isExecutable(int joinClass, int n){
        return joinClass == n;
    }

    static int checkAndChange(Student[] students, int left, int right, int min){
        return Math.min(students[right].ability - students[left + 1].ability, min);
    }

    static class Student {
        int classroom;
        int ability;

        Student(int classroom, int ability) {
            this.classroom = classroom;
            this.ability = ability;
        }
    }
}