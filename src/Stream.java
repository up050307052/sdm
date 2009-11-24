
import java.io.Serializable;

public class Stream implements Serializable{
    protected int x;
    protected StreamType type;

    public Stream (int val, StreamType t) {
        x = val;
        type = t;
    }
}
