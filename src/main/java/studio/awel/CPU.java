package studio.awel;

import java.util.List;

public class CPU {

    private int[] registers = new int[4];
    private int pc = 0;
    private boolean halted = false;
    private List<Instructions> program;
    private int clockHz = 60;

    public CPU(List<Instructions> program){
        this.program = program;
    }

    public void run() throws InterruptedException {
        while (!halted) {
            step();
            if (clockHz > 0) Thread.sleep(1000 / clockHz);
        }
    }

    private void step(){
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
            //case "JLT":
            //    if (registers[ins.a] < registers[ins.b]){
            //        pc = ins.c potential third variable if I want to add ARM64 realistic commands
            //    }
            //    break;
            case "ADD":
                registers[ins.a] += registers[ins.b];
                break;
            case "SUB":
                registers[ins.a] -= registers[ins.b];
                break;
            case "PRINT":
                System.out.println(registers[ins.a]);
                break;
            default:
                throw new IllegalArgumentException("Unknown opcode: " + ins.opcode);
        }
    }
}