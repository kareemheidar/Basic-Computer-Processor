import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Processor {
    static int[] Memory;
    static int[] Registers;
    int PC;
    final int zero;

    Processor(){
        Memory = new int[2048];
        zero = 0;
        Registers = new int[31];
        PC = 0;
    }

    public static int readInstruction(String line){
        String[] Line = line.split(" ");
        String operation = Line[0];
        int instruction=0;
        int opcode;

        int R1 ;
        int R2 ;
        int R3  ;
        int shamt;
        int imm ;
        int address;
        switch (operation){
            case "ADD":
                opcode = 0<<28;
                R1 = getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                R3 = getRegister(Line[3])<<13;
                shamt = 0;
                instruction = opcode | R1 | R2 | R3 | shamt;
                break;
            case "SUB":
                opcode = 1<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                R3 =  getRegister(Line[3])<<13;
                shamt = 0;
                instruction = opcode | R1 | R2 | R3 | shamt;
                break;
            case "MULI":
                opcode = 2<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;
            case "ADDI":
                opcode = 3<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;
            case "BNE":
                opcode = 4<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;
            case "ANDI":
                opcode = 5<<28;
                R1 =  getRegister(Line[1])<<24;
                R2 =  getRegister(Line[2])<<19;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;
            case "ORI":
                opcode = 6<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;
            case "J":
                opcode = 7<<28;
                address= Integer.parseInt(Line[1]);
                instruction = opcode | address;
                break;

            case "SLL":
                opcode = 8<<28;
                R1 = getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                shamt = Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2  | shamt;
                break;
            case "SRL":
                opcode = 9<<28;
                R1 = getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                shamt = Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2  | shamt;
                break;
            case "LW":
                opcode = 10<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;
            case "SW":
                opcode = 11<<28;
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
                imm= Integer.parseInt(Line[3]);
                instruction = opcode | R1 | R2 | imm;
                break;



        }
        return instruction;
    }

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
    public void fetch() {
        // Complete the fetch() body...
//        for (int ins = 0; ins < 1024; ins++) {
//            decode(ins);
//            PC++;
//        }
        decode(Memory[0]);
        PC++;
        // Complete the fetch() body...
    }

    public void decode(int instruction) {

        int opcode = 0;  // bits31:28
        int rs = 0;      // bits27:24
        int rt = 0;      // bit23:20
        int rd = 0;      // bits19:16
        int shamt = 0;   // bits15:12
        int imm = 0;     // bits19:0
        int address = 0; // bits27:0

        int valueRS = 0;
        int valueRT = 0;

        opcode = instruction & 0b11110000000000000000000000000000;
        opcode = opcode >>> 28;

        rd = instruction & 0b00001111100000000000000000000000;
        rd = rd >>> 23;

        rs = instruction & 0b00000000011111000000000000000000;
        rs = rs >>> 18;

        rt = instruction & 0b00000000000000111110000000000000;
        rt = rt >>> 13;

        shamt = instruction & 0b00000000000000000001111111111111;

        imm = instruction & 0b00000000000000111111111111111111;

        address = instruction & 0b00001111111111111111111111111111;

        valueRS = Registers[rs];

        valueRT = Registers[rt];

        execute(opcode,valueRS,valueRT,rd,shamt,imm,address);


        System.out.println("Instruction "+PC);
        System.out.println("opcode = "+opcode);
        System.out.println("rs = "+rs);
        System.out.println("rt = "+rt);
        System.out.println("rd = "+rd);
        System.out.println("shift amount = "+shamt);

        System.out.println("immediate = "+imm);
        System.out.println("address = "+address);
        System.out.println("value[rs] = "+valueRS);
        System.out.println("value[rt] = "+valueRT);
        System.out.println("----------");
    }
    public int ALU(int operandA, int operandB, int operation) {

        int output = 0;
        int zeroFlag = 0;

        switch(operation){
            case 0:
                //ADD R1 R2 R3
                output = operandA + operandB;
                break;
            case 1:
                //SUB R1 R2 R3
                output = operandA - operandB;
                break;
            case 2:
                //MULI R1 R2 IMM
                output = operandA * operandB;
                break;
            case 3:
                //ADDI R1 R2 IMM
                output = operandA + operandB;
                break;
            case 5:
                //ANDI R1 R2 IMM
                output = operandA & operandB;
                break;
            case 6:
                //ORI R1 R2 IMM
                output = operandA | operandB;
                break;
            case 8:
                //SLL R1 R2 SHAMT
                output = operandA << operandB;
            case 9:
                //SRL R1 R2 SHAMT
                output = operandA >> operandB;
                break;
        }
        if(output == 0){
            zeroFlag = 1;
        }

        System.out.println("Operation = "+operation);
        System.out.println("First Operand = "+operandA);
        System.out.println("Second Operand = "+operandB);
        System.out.println("Result = "+output);
        System.out.println("Zero Flag = "+zeroFlag);

        return output;
    }

    public void BNE(int operandA, int operandB, int operandC) {
        if (operandA!=operandB){
            PC = PC + 1 + operandC;
        }
    }
    public void Jump(int address) {
        int leftMostPC = PC & 0b11110000000000000000000000000000;
        PC = leftMostPC | address;
    }
    public int checkType(int opcode){
        switch (opcode){
            case 0:
            case 1:
            case 8:
            case 9: return 1;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 10:
            case 11: return 2;
            case 7: return 3;
            default:return 0;
        }
    }

    public void execute(int opcode,int valueRS,int valueRT,int rd,int shamt,int imm,int address){
        int type = checkType(opcode);
        int output;
        if (type == 1){
            if (opcode == 8 || opcode == 9){
                Registers[rd] = ALU(valueRS,shamt,opcode);
            }
            else{
                Registers[rd] = ALU(valueRS,valueRT,opcode);
            }
        }
        else if (type == 2){
            if (opcode == 4){
                BNE(valueRS,valueRT,imm);
            }
            else if (opcode == 10) {
                //Read from memory
            }
            else if (opcode == 11){
                //Write in memory
            }
            else {
                Registers[rd] = ALU(valueRS,imm,opcode);
            }
        }
        else {
            Jump(address);
        }
    }

    static void saveMemory(int R1,int R2,int imm) {
        int valueOfR2 = Registers[R2];
        int index = imm + valueOfR2;
        if(index>1023) {
            Memory[index] = Registers[R1];
        }else {
            System.out.println("This memory location is reserved for the Instructions");
        }


    }

    static void loadMemory(int R1,int R2,int imm) {
        int valueOfR2 = Registers[R2];
        int index = imm + valueOfR2;
        Registers[R1] = Memory[index];

    }

    public static String readFile(String argument) throws IOException {
        String filePath = argument;
        filePath += ".txt"; //Program.txt
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            // delete the last new line separator
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("No File Exists");
            return "No File Exists";
        }

    }

    public static void runProgram(String filePath) throws IOException {
        String dataOfTheFile = readFile(filePath);
        String[] lines = dataOfTheFile.trim().split("\\n+");
        for (String line:lines) {
//            readInstruction(line);
            System.out.println(line);
        }
    }

    public static void main(String[] args) throws IOException {
        Processor p = new Processor();
        Registers[0] =20;
        Registers[1] =35;
        String i = "SUB R7 R1 R0";
        System.out.println(Registers[7] + "  "+ Registers[1] +"  "+ Registers[0]);
        System.out.println(readInstruction(i));
        Memory[0] = readInstruction(i);
        p.fetch();
        runProgram("src/Test11");



    }
}