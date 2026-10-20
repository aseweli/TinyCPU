package studio.awel;

import java.util.List;

public class CPU {

    private final int[] registers = new int[4];
    private final List<Instructions> program;
    private final Memory memory = new Memory(256, 255, 254);

    private int pc = 0;
    private boolean halted = false;

    int clockHz = 60;

    int getPc(){return pc;}
    boolean isHalted(){return halted;}
    int getRegister(int i){return registers[i];}

    void setClockHz(int hz){
        if (hz <= 0) throw new IllegalArgumentException("Clock frequency must be positive");
        clockHz = hz;
    }

    public CPU(List<Instructions> program){
        this.program = program;
    }

    public void run() throws InterruptedException {
        while (!halted) {
            step();
            Thread.sleep(1000 / clockHz);
        }
    }

    void step(){
        Instructions ins = program.get(pc);
        pc++;
        switch (ins.opcode){
            case "LOAD":
                registers[ins.a] = ins.b;
                break;
            case "HALT":
                halted = true;
                break;
            case "NOP":
                break;
            case "JMP":
                pc = ins.a;
                break;
            case "JZ":
                if (registers[ins.a] == 0) {
                    pc = ins.b;
                }
                break;
            case "MOV":
                registers[ins.a] = registers[ins.b];
                break;
            case "JLT":
                if (registers[ins.a] < registers[ins.b]){
                    pc = ins.c;
                }
                break;
            case "ADD":
                registers[ins.a] += registers[ins.b];
                break;
            case "SUB":
                registers[ins.a] -= registers[ins.b];
                break;
            case "STR":
                memory.write(ins.b, registers[ins.a]);
                break;
            case "LDR":
                registers[ins.a] = memory.read(ins.b);
                break;
            case "PRINT":
                System.out.println(registers[ins.a]);
                break;
            default:
                throw new IllegalArgumentException("Unknown opcode: " + ins.opcode);
        }
    }
}