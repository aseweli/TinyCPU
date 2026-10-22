package studio.awel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Assembler {

    public static List<Instructions> assemble(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        lines.replaceAll(line -> line.split("#")[0]);
        lines.removeIf(String::isBlank);

        return lines.stream().map(line -> {
            String[] parts = line.trim().split(" ");
            String opcode = parts[0];
            int a = parts.length > 1 ? Integer.parseInt(parts[1].replace("R", "")) : 0;
            int b = parts.length > 2 ? Integer.parseInt(parts[2].replace("R", "")) : 0;
            int c = parts.length > 3 ? Integer.parseInt(parts[3].replace("R", "")) : 0;
            return new Instructions(opcode, a, b, c);
        }).toList();

    }
}
