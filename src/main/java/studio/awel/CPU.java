package studio.awel;

import java.util.List;

public class CPU {

    private int[] registers = new int[4];
    private int pc = 0;
    private boolean halted = false;
    private List<Instructions> program;

    public CPU(List<Instructions> program){
        this.program = program;
    }

    public void run() {
        while (!halted) {
            step();
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
            case "ADD":
                registers[ins.a] += registers[ins.b];
                break;
            case "PRINT":
                System.out.print(registers[ins.a]);
                break;
        }
    }
}