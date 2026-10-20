package studio.awel;

public class Memory {
    private final int[] cells;
    public final int screenPort;
    public final int numberPort;

    public Memory(int size, int screenPort, int numberPort) {
        cells = new int[size];
        this.screenPort = screenPort;
        this.numberPort = numberPort;
    }

    int read(int address){
        if (address < 0 || address >= cells.length) throw new IndexOutOfBoundsException("Address out of bounds");
        return cells[address];
    }

    void write(int address, int value){
        if (address == screenPort){
            System.out.print((char) value);
            return;
        }
        if (address == numberPort){
            System.out.println(value);
            return;
        }
        if (address < 0 || address >= cells.length) throw new IndexOutOfBoundsException("Address out of bounds");
        cells[address] = value;
    }
}
