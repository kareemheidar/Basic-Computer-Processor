import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Processor {
    static int[] Memory;
    static int[] Registers;
    int PC;
    final int zero;
    int zeroFlag = 0;
    int numberOfInstructions = 0;
    int instructionNumber = 1;
    int pointer;

    HashMap <Integer, int[]> fetchedInstructions = new HashMap<>();




    Processor(){
        Memory = new int[2048];
        zero = 0;
        Registers = new int[32];
        PC = 0;
    }

    public int readInstruction(String line){
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
                R1 =  getRegister(Line[1])<<23;
                R2 =  getRegister(Line[2])<<18;
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

        return Integer.parseInt(r);
    }
    public void fetch() {
        fetchedInstructions.put(instructionNumber, new int[15]);
        fetchedInstructions.get(instructionNumber)[0]=Memory[PC];
        instructionNumber++;
        PC++;
    }

    public void decode(int instruction,int instructionId) {

        int opcode;
        int rd;
        int rs;
        int rt;
        int shamt;
        int imm;
        int address;
        int valueRS;
        int valueRT;
        int valueRD;
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

        valueRD = Registers[rd];


        fetchedInstructions.get(instructionId)[1] = opcode;
        fetchedInstructions.get(instructionId)[2] = rd;
        fetchedInstructions.get(instructionId)[3] = rs;
        fetchedInstructions.get(instructionId)[4] = rt;
        fetchedInstructions.get(instructionId)[5] = shamt;
        fetchedInstructions.get(instructionId)[6] = imm;
        fetchedInstructions.get(instructionId)[7] = address;
        fetchedInstructions.get(instructionId)[8] = valueRS;
        fetchedInstructions.get(instructionId)[9] = valueRT;
        fetchedInstructions.get(instructionId)[10] = valueRD;


//
//        System.out.println("Instruction "+PC);
//        System.out.println("opcode = "+opcode);
//        System.out.println("rs = "+rs);
//        System.out.println("rt = "+rt);
//        System.out.println("rd = "+rd);
//        System.out.println("shift amount = "+shamt);
//
//        System.out.println("immediate = "+imm);
//        System.out.println("address = "+address);
//        System.out.println("value[rs] = "+valueRS);
//        System.out.println("value[rt] = "+valueRT);
//        System.out.println("value[rd] = "+valueRD);
//        System.out.println("----------");
    }
    public void ALU(int operandA, int operandB, int operation,int[] instruction) {
        int output = 0;

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
                break;
            case 9:
                //SRL R1 R2 SHAMT
                output = operandA >> operandB;
                break;
        }
        if(output == 0){
            zeroFlag = 1;
        }
        instruction[11] = output;

//        System.out.println("Operation = "+operation);
//        System.out.println("First Operand = "+operandA);
//        System.out.println("Second Operand = "+operandB);
//        System.out.println("Result = "+output);
//        System.out.println("Zero Flag = "+zeroFlag);
//        System.out.println(instruction[]);
    }

    public void BNE(int operandA, int operandB, int operandC) {
        if (operandA!=operandB){
            PC = PC + operandC;
        }
    }
    public void Jump(int address) {
        int leftMostPC = PC & 0b11110000000000000000000000000000;
        PC = leftMostPC | address -1;

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

    public void execute(int[] instruction){
        int opcode = instruction[1];
        int shamt = instruction[5];
        int imm = instruction[6];
        int address = instruction[7];
        int valueRS = instruction[8];
        int valueRT = instruction[9];
        int type = checkType(opcode);
        if (type == 1){
            if (opcode == 8 || opcode == 9){
                ALU(valueRS,shamt,opcode, instruction);
            }
            else{
                ALU(valueRS,valueRT,opcode, instruction);
            }
        }
        else if (type == 2){
            if (opcode == 4){
                BNE(valueRS,valueRT,imm);
            }
            else if (opcode == 10) {
                //Read from memory
                loadWord(instruction);
            }
            else if (opcode == 11){
                //Write in memory
                storeWord(instruction);
            }
            else {
                ALU(valueRS,imm,opcode, instruction);
            }
        }
        else {
            Jump(address);
        }
    }

    public void storeWord(int[] instruction) {

        int index = instruction[6] + instruction[8];
        if(index>1023) {
            instruction[14] = index;
        }else {
            System.out.println("This memory location is reserved for the Instructions");
        }
    }

    public void loadWord(int[] instruction) {
        instruction[13] = instruction[6] + instruction[8];
    }
    public void memoryAccess(int[] instruction){
        int opcode = instruction[1];
        int valueRD = instruction[10];
        int LW_index = instruction[13];
        int SW_index = instruction[14];
        if (opcode == 10){
            instruction[12] = Memory[LW_index];
        }
        else if(opcode == 11){
            Memory[SW_index] = valueRD;
        }
    }
    public void writeBack(int[] instruction){
        int opcode = instruction[1];
        int rd = instruction[2];
        int output = instruction[11];
        int LW_WriteBack = instruction[12];
        if (opcode != 4 || opcode != 7 || opcode != 11){
            if (opcode == 10)
                Registers[rd] = LW_WriteBack;
            else Registers[rd] = output;
        }
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

    public void pipeline(){
        int CLK = 7+((numberOfInstructions-1)*2);
        int i =1;
        pointer = 1;
        fetch();
        while (i<=CLK){
            if(i>1){
                if(i%2==0){
                    if (fetchedInstructions.containsKey(pointer-2)){
                        memoryAccess(fetchedInstructions.get(pointer-2));
                    }
                    if (fetchedInstructions.containsKey(pointer)) {
                        decode(fetchedInstructions.get(pointer)[0], pointer);
                        pointer++;
                    }

                }else{
                    if (fetchedInstructions.containsKey(pointer-3)){
                        writeBack(fetchedInstructions.get(pointer-3));
                    }
                    if(fetchedInstructions.containsKey(pointer-2)){
                        execute(fetchedInstructions.get(pointer-2));

                    }
                    fetch();
                }
            }
            i++;
        }
    }

    public void runProgram(String filePath) throws IOException {
        String dataOfTheFile = readFile(filePath);
        String[] lines = dataOfTheFile.trim().split("\\n+");
        numberOfInstructions = lines.length;
        int c = 0;
        for (String line:lines) {
            Memory[c] = readInstruction(line);
            c++;
            System.out.println(line);
        }
        pipeline();
    }

    public static void main(String[] args) throws IOException {
        Processor p = new Processor();
        p.runProgram("src/Test11");
        System.out.println(Arrays.toString(Registers));
        System.out.println("STORE: "+ Memory[2047]);
        System.out.println("STORE: "+ Memory[2047]);




    }

}