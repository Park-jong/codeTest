import java.io.*;
import java.util.StringTokenizer;

public class Main_32405_배틀로얄 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Player[] players = new Player[n];
        for (int i = 0; i < n; i++) {
            players[i] = new Player(i + 1);
        }
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            players[i].prev = players[(i + n - 1) % n];
            players[i].next = players[(i + 1) % n];
            players[i].damage = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            players[i].hp = Integer.parseInt(st.nextToken());
        }
        int result = findWinner(n, players);
        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int findWinner(int n, Player[] players) {
        int totalAttack = 0;
        Player player = players[0];
        for (int count = n; count > 1; player = player.next) {
            totalAttack += player.damage;
            player.visit++;
            if (player.hp <= totalAttack - (player.damage * player.visit)) {
                totalAttack -= player.damage;
                player.remove();
                count--;
            }
        }
        return player.index;
    }

    static class Player {
        int index;
        int hp;
        int damage;
        int visit;
        Player prev;
        Player next;

        Player(int index) {
            this.index = index;
        }

        void remove() {
            Player p = this.prev;
            this.prev.next = this.next;
            this.next.prev = p;
        }
    }
}