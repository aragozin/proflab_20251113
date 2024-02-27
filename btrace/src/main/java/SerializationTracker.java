// This is BTrace 1 script, will not run with BTrace2

import org.openjdk.btrace.core.annotations.*;
import static org.openjdk.btrace.core.BTraceUtils.*;

@BTrace(unsafe = true)
public class SerializationTracker {

    @TLS
    public static int depth = 0;

    @OnMethod(clazz="java.io.ObjectOutputStream", method="writeObject")
    public static void enterWrite(Object param) {
        ++depth;
    }

    @OnMethod(clazz="java.io.ObjectOutputStream", method="writeObject", location=@Location(value=Kind.RETURN))
    public static void leaveWrite(Object param, @Duration long time) {
        depth = 0;
        if (depth == 0 && param != null) {
            println("writeObject: " + param.getClass().getName() + " - " + time);
        }
    }

    @OnMethod(clazz="java.io.ObjectInputStream", method="readObject")
    public static void enterRead(Object param) {
        ++depth;
    }

    @OnMethod(clazz="java.io.ObjectInputStream", method="readObject", location=@Location(value=Kind.RETURN))
    public static void leaveRead(Object param, @Duration long time) {
        depth = 0;
        if (depth == 0 && param != null) {
            println(" readObject: " + param.getClass().getName() + " - " + time);
        }
    }
}
