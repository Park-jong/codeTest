import java.io.*;

public class Main_7682_틱택토 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String board = br.readLine();
            if (board.equals("end")) break;
            int xCount = 0;
            int oCount = 0;
            int xBit = 0;
            int oBit = 0;
            boolean result = false;
            for (int i = 0; i < 9; i++) {
                char c = board.charAt(i);
                if (c == 'X') {
                    xCount++;
                    xBit += (1 << i);
                } else if (c == 'O') {
                    oCount++;
                    oBit += (1 << i);
                }
            }
            boolean xBingo = checkBoardResult(xBit);
            boolean oBingo = checkBoardResult(oBit);
            if (xBingo && !oBingo && xCount == oCount + 1)
                result = true;
            else if (oBingo && !xBingo && xCount == oCount)
                result = true;
            else if (!xBingo && !oBingo && xCount + oCount == 9 && xCount == oCount + 1)
                result = true;
            sb.append(result ? "valid" : "invalid").append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    static boolean checkBoardResult(int bit) {
        return (bit & 7) == 7 ||
                (bit & 56) == 56 ||
                (bit & 448) == 448 ||
                (bit & 73) == 73 ||
                (bit & 146) == 146 ||
                (bit & 292) == 292 ||
                (bit & 84) == 84 ||
                (bit & 273) == 273;
    }
}