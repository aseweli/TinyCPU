package studio.awel;

public class Instructions{

    public final String opcode;
    public final int a;
    public final int b;

    public Instructions(String opcode, int a, int b){
        this.opcode = opcode;
        this.a = a;
        this.b = b;
    }
}