import java.io.*;
import java.util.StringTokenizer;

public class Main_20008_몬스터를처치하자 {

    static int minTime = Integer.MAX_VALUE;
    static int n;
    static Skill[] skills;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int hp = Integer.parseInt(st.nextToken());
        skills = new Skill[n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int coolDown = Integer.parseInt(st.nextToken());
            int damage = Integer.parseInt(st.nextToken());
            skills[i] = new Skill(coolDown, damage);
        }
        attack(0, hp);
        bw.write(String.valueOf(minTime));
        bw.close();
        br.close();
    }

    static void attack(int time, int hp){
        if(minTime <= time){
            return;
        }
        if(killMonster(hp)){
            minTime = Math.min(minTime, time);
            return;
        }
        int nearTime = Integer.MAX_VALUE;
        boolean useSkill = false;
        for(int i = 0; i < n; i++){
            if(canUse(skills[i], time)){
                useSkill = true;
                int nextTime = time + 1;
                int remainHP = hp - skills[i].damage;
                int temp = skills[i].canUseTime;
                skills[i].canUseTime = time + skills[i].coolDown;
                attack(nextTime, remainHP);
                skills[i].canUseTime = temp;
            }
            else {
                nearTime = Math.min(skills[i].canUseTime, nearTime);
            }
        }
        if(!useSkill){
            attack(nearTime, hp);
        }
    }

    static boolean canUse(Skill skill, int time){
        return skill.canUseTime <= time;
    }

    static boolean killMonster(int hp){
        return hp <= 0;
    }

    static class Skill{
        int coolDown;
        int damage;
        int canUseTime;
        Skill(int coolDown, int damage){
            this.coolDown = coolDown;
            this.damage = damage;
        }
    }
}