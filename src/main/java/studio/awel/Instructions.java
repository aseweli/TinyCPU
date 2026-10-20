package studio.awel;

public class Instructions{

    public final String opcode;
    public final int a;
    public final int b;
    public final int c;

    public Instructions(String opcode, int a){
        this.opcode = opcode; this.a = a; this.b = 0; this.c = 0;
    }

    public Instructions(String opcode, int a, int b){
        this.opcode = opcode; this.a = a; this.b = b; this.c = 0;}

    public Instructions(String opcode, int a, int b, int c){
        this.opcode = opcode; this.a = a; this.b = b; this.c = c;}
    }
