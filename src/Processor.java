import java.util.HashMap;

public class Processor {
    String[] Memory;
    int[] Registers;
    int PC;

    Processor(){
        Memory = new String[2048];
        Registers = new int[32];
        PC = 0;
    }

//    public static String readInstruction(String line){
//        String[] Line = line.split(" ");
//        String operation = Line[0];
//        String instruction = "";
//        String opcode = "";
//
//        String R1 ;
//        String R2 ;
//        String R3 = "";
//        String shamt = "";
//        String imm = "";
//        String address = "";
//        switch (operation){
//            case "ADD":
//                opcode = "0000";
//                R1 = fillZeroes("5", getRegister(Line[1]));
//                R2 = fillZeroes("5", getRegister(Line[2]));
//                R3 = fillZeroes("5", getRegister(Line[3]));
//                shamt = "0000000000000";
//                instruction = opcode + R1 + R2 + R3 + shamt;
//                break;
//            case "SUB":
//                opcode = "0001";
//                R1 = fillZeroes("5", getRegister(Line[1]));
//                R2 = fillZeroes("5", getRegister(Line[2]));
//                R3 = fillZeroes("5", getRegister(Line[3]));
//                shamt = "0000000000000";
//                instruction = opcode + R1 + R2 + R3 + shamt;
//                break;
//            case "MULI":
//                opcode = "0010";
//                R1 = fillZeroes("5", getRegister(Line[1]));
//                R2 = fillZeroes("5", getRegister(Line[2]));
//
//                shamt = "0000000000000";
//                instruction = opcode + R1 + R2 + R3 + shamt;
//                break;
//            case "ADDI":
//                break;
//
//
//        }
//    }
    public static int getRegister(String R){
        String r = "";
        if (R.length() == 2){
            r = r + R.charAt(1);
        }else {
            r = r + R.charAt(1) + R.charAt(2);
        }
        int n = Integer.parseInt(r);

        return n;
    }
    public static String fillZeroes(String max, int number){
            max = "%0"+max+"d";
            return String.format(max, number);

    }
    public static String getImmediate(int imm){
        String s;
        if (imm < 0){
            s = Integer.toBinaryString(imm);
            s =  s.substring(14,32);
        }
        else {
            int i = Integer.parseInt(Integer.toBinaryString(imm));
            s = fillZeroes("18", i);
        }
        return s;
    }

    public static void main(String[] args) {
        String G = "11";
        String s = getImmediate(-5);
        int n = Integer.parseInt(s,2);
        System.out.println(s);
        System.out.println(n);



    }
}