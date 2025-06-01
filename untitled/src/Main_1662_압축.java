import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main_1662_압축 {

    static int n;
    static String str;
    static Deque<Character> stack = new ArrayDeque<>();
    static int index;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        str = br.readLine();
        n = str.length();
        index = 0;
        int length = 0;
        length  = addLength(1);
        bw.write(String.valueOf(length));
        bw.close();
        br.close();
    }

    static int addLength(int repeat) {
        int length = 0;
        for(; index < n; index++){
            char c = str.charAt(index);
            if(c == ')'){
                return length * repeat;
            }
            if(c == '('){
                length--;
                int top = stack.pop() - '0';
                index++;
                length += addLength(top);
            }
            else {
                stack.push(c);
                length++;
            }
        }
        return length;
    }
}