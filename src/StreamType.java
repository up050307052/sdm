
import java.io.Serializable;

public class StreamType implements Serializable {
    protected static final int VIDEO = 0;
    protected static final int AUDIO = 1;
    protected static final int TEXT  = 2;
    protected static final int INT   = 3;
    protected int type = -1;

    public StreamType(int type) {
        this.type = type;
    }
}
