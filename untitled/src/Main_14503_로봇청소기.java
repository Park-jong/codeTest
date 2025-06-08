import java.io.*;
import java.util.StringTokenizer;

public class Main_14503_로봇청소기 {

    static int[] directionR = {-1, 0, 1, 0};
    static int[] directionC = {0, 1, 0, -1};
    static int n;
    static int m;
    static int r;
    static int c;
    static int d;
    static int[][] room;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        room = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                room[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = cleanRoom();
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int cleanRoom() {
        int result = 0;
        while (true) {
            result += cleanArea();
            if (!checkAndMove()) break;
        }
        return result;
    }

    static int cleanArea() {
        if (room[r][c] == 0) {
            room[r][c] = 2;
            return 1;
        }
        return 0;
    }

    static boolean checkAndMove() {
        boolean cleanPossible = check();
        if (!cleanPossible) {
            return backward();
        }
        rotateAndForward();
        return true;
    }

    static boolean check() {
        for (int i = 0; i < 4; i++) {
            int checkR = r + directionR[i];
            int checkC = c + directionC[i];
            if (checkR >= 0 && checkC >= 0 && checkR < n && checkC < m && room[checkR][checkC] == 0) {
                return true;
            }
        }
        return false;
    }

    static boolean backward() {
        int backR = r - directionR[d];
        int backC = c - directionC[d];
        if (backR >= 0 && backC >= 0 && backR < n && backC < m && room[backR][backC] != 1) {
            r = backR;
            c = backC;
            return true;
        }
        return false;
    }

    static void rotateAndForward() {
        d = (d + 3) % 4;
        int goR = r + directionR[d];
        int goC = c + directionC[d];
        if (goR >= 0 && goC >= 0 && goR < n && goC < m && room[goR][goC] == 0) {
            r = goR;
            c = goC;
        }
    }
}