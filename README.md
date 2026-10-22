# TinyCPU

TinyCPU is a small CPU emulator written in Java. It has its own instruction set, an assembler that reads `.asm` text files, 256 cells of memory, memory-mapped output, and an adjustable clock. I built it to learn how processors actually work: the fetch-decode-execute loop, registers, branching, and talking to devices through memory.

```
TINYCPU
     *
    ***
   *****
  *******
 *********
***********
SQUARES
1
4
9
...
```
*Output of `programs/showcase.asm`: text, nested loops, and math, all running on TinyCPU.*

## How it works

Each clock tick, the CPU does one **fetch → decode → execute** cycle:

1. **Fetch** the instruction at the program counter (`pc`), then move `pc` forward by one.
2. **Decode** the opcode (`LOAD`, `ADD`, `JMP`, …).
3. **Execute** it by updating registers, memory, or `pc`.

This repeats until a `HALT` instruction runs.

| Part | Class | What it does |
|---|---|---|
| CPU | `CPU.java` | 4 registers (`R0`–`R3`), the program counter, and the execute loop |
| Memory | `Memory.java` | 256 integer cells, plus two output ports |
| Assembler | `Assembler.java` | Turns `.asm` text into `Instructions` |
| Instruction | `Instructions.java` | An opcode and up to three operands |

## Instruction set

Line numbers start at **0**. Jump targets are line numbers.

| Instruction | Example | What it does |
|---|---|---|
| `LOAD` | `LOAD R0 5` | Put the number 5 into R0 |
| `MOV` | `MOV R1 R0` | Copy R0 into R1 |
| `ADD` | `ADD R0 R1` | R0 = R0 + R1 |
| `SUB` | `SUB R0 R1` | R0 = R0 − R1 |
| `STR` | `STR R0 10` | Store R0 into memory cell 10 |
| `LDR` | `LDR R0 10` | Load memory cell 10 into R0 |
| `JMP` | `JMP 3` | Jump to line 3 |
| `JZ` | `JZ R0 7` | If R0 is 0, jump to line 7 |
| `JLT` | `JLT R0 R1 7` | If R0 < R1, jump to line 7 |
| `NOP` | `NOP` | Do nothing for one clock tick |
| `PRINT` | `PRINT R0` | Print R0 (debug instruction) |
| `HALT` | `HALT` | Stop the CPU |

An unknown opcode throws an error instead of being silently skipped.

## Memory-mapped output

Like small ARM and RISC-V boards, TinyCPU sends output by **writing to special memory addresses** instead of using a print instruction.

| Address | Device | Example |
|---|---|---|
| `255` | Character screen, prints the value as ASCII | `LOAD R0 72` then `STR R0 255` prints `H` |
| `254` | Number port, prints the value as a number | `STR R0 254` prints `42` |

## Clock

The CPU runs at **60 Hz** by default, so each instruction takes one tick. Programs can make real-time delays with `NOP` loops. For example, `timer.asm` counts down 10 seconds by spending exactly 60 ticks per second.

## Example programs

| Program | What it does |
|---|---|
| `add.asm` | Adds four registers together |
| `multiply.asm` | 6 × 7 using a loop of additions → `42` |
| `sum.asm` | 10 + 9 + … + 1 → `55` |
| `fibonacci.asm` | First 10 Fibonacci numbers |
| `factorial.asm` | 5! using nested loops and memory → `120` |
| `timer.asm` | 10-second real-time countdown |
| `showcase.asm` | Text banner, ASCII pyramid, and square numbers |

## Running it

Requirements: Java 19+.

1. Open the project in IntelliJ.
2. In `Main.java`, set the program path, for example `"programs/fibonacci.asm"`.
3. Run `Main`.

Writing your own program: one instruction per line, operands separated by single spaces. Anything after `#` on a line is a comment.

## Tests

The JUnit tests cover every instruction (both branch outcomes for `JZ` and `JLT`), memory writes, unknown opcodes, `HALT`, and a full program loaded from a file.

```
./gradlew test
```

## What's next

- Labels in the assembler (`JMP loop` instead of `JMP 23`)
- `CALL` / `RET` and a stack for functions
- Fixed-width 8-bit registers with explicit overflow
- A hardware version in Verilog, using this Java emulator as the reference model
