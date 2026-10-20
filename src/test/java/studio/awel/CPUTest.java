package studio.awel;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    @Test
    void loadPutsNumberInRegister() {
        CPU cpu = new CPU(List.of(new Instructions("LOAD", 2, 7)));
        cpu.step();
        assertEquals(7, cpu.getRegister(2));
        assertEquals(1, cpu.getPc());
    }

    @Test
    void addAddsTwoRegisters() {
        CPU cpu = new CPU(List.of(
                new Instructions("LOAD", 0, 5),
                new Instructions("LOAD", 1, 3),
                new Instructions("ADD", 0, 1)
        ));
        cpu.step(); cpu.step(); cpu.step();
        assertEquals(8, cpu.getRegister(0));
    }

    @Test
    void jzJumpsWhenZero() {
        CPU cpu = new CPU(List.of(
                new Instructions("JZ", 0, 5)
        ));
        cpu.step();
        assertEquals(5, cpu.getPc());
    }

    @Test
    void jltJumpsWhenLessThan() {
        CPU cpu = new CPU(List.of(
                new Instructions("LOAD", 0, 2),
                new Instructions("LOAD", 1, 3),
                new Instructions("JLT", 0, 1, 5)
        ));
        cpu.step(); cpu.step(); cpu.step();
        assertEquals(5, cpu.getPc());
    }

    @Test
    void subRemovesTwoRegisters() {
        CPU cpu = new CPU(List.of(
                new Instructions("LOAD", 0, 5),
                new Instructions("LOAD", 1, 3),
                new Instructions("SUB", 0, 1)
        ));
        cpu.step(); cpu.step(); cpu.step();
        assertEquals(2, cpu.getRegister(0));
    }

    @Test
    void movCopiesRegisters() {
        CPU cpu = new CPU(List.of(
                new Instructions("LOAD", 0, 5),
                new Instructions("MOV", 1, 0)
        ));
        cpu.step(); cpu.step();
        assertEquals(5, cpu.getRegister(1));
    }

    @Test
    void jmpJumpsRegisters() {
        CPU cpu = new CPU(List.of(
                new Instructions("LOAD", 0, 5),
                new Instructions("LOAD", 1, 3),
                new Instructions("LOAD", 2, 20),
                new Instructions("JMP", 1)
        ));
        cpu.step(); cpu.step(); cpu.step(); cpu.step();
        assertEquals(1, cpu.getPc());
    }

    @Test
    void jzDoesNotJumpWhenNotZero() {
        CPU cpu = new CPU(List.of(
                new Instructions("LOAD", 0, 5),
                new Instructions("JZ", 0, 5)
        ));
        cpu.step(); cpu.step();
        assertEquals(2, cpu.getPc());
    }
}