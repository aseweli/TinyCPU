package studio.awel;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        List<Instructions> program;
        try{
            program = Assembler.assemble("programs/showcase.asm");
        } catch (IOException e){
            program = List.of(
                    new Instructions("LOAD", 0, 5),
                    new Instructions("LOAD", 1, 3),
                    new Instructions("ADD", 0, 1),
                    new Instructions("PRINT", 0, 0),
                    new Instructions("HALT", 0, 0)
            );
            System.out.println("Error reading file: " + e.getMessage());
        }
        CPU cpu = new CPU(program);
        try{
            cpu.run();
        } catch (InterruptedException e){
            System.out.print("Error executing file!");
        }
    }
}