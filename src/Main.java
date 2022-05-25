import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static HashMap<Integer,int[]> lala = new HashMap<>();
    public static void changer(int[] s) {
        s[0] = 123;
    }
    public static void changerOG(int k) {
        int[] temp = lala.get(k);
        temp[0] = 123456789;
        lala.replace(1,temp);
    }
    public static void main(String[] args) {
        int [] khara = {1,2};
        lala.put(1,khara);
        changer(lala.get(1));
        System.out.println("After Changer: "+ Arrays.toString(lala.get(1)));
        System.out.println();
        changerOG(1);
        System.out.println("After ChangerOG: "+ lala.get(1)[0]);
    }
}