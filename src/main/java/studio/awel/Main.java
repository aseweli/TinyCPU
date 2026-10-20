package studio.awel;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Instructions> program = List.of(
                new Instructions("LOAD", 0, 5),
                new Instructions("LOAD", 1, 3),
                new Instructions("ADD", 0, 1),
                new Instructions("PRINT", 0, 0),
                new Instructions("HALT", 0, 0)
        );
        CPU cpu = new CPU(program);
        cpu.run();
    }
}