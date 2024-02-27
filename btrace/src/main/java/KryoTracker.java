// This is BTrace 1 script, will not run with BTrace2

import static org.openjdk.btrace.core.BTraceUtils.println;

import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.Duration;
import org.openjdk.btrace.core.annotations.Kind;
import org.openjdk.btrace.core.annotations.Location;
import org.openjdk.btrace.core.annotations.OnMethod;
import org.openjdk.btrace.core.annotations.Return;
import org.openjdk.btrace.core.annotations.TLS;
import org.openjdk.btrace.core.types.AnyType;

@BTrace(unsafe = true)
public class KryoTracker {

    @TLS
    public static int depth = 0;

    @OnMethod(clazz="info.ragozin.perflab.hazelagg.kryo.KryoSerializer", method="write", location=@Location(value=Kind.RETURN))
    public static void leaveWrite(AnyType param, @Duration long time) {
        depth = 0;
        if (depth == 0 && param != null) {
            println("writeObject: " + param.getClass().getName() + " - " + time);
        }
    }

    @OnMethod(clazz="info.ragozin.perflab.hazelagg.kryo.KryoSerializer", method="read", location=@Location(value=Kind.RETURN))
    public static void leaveRead(AnyType objectDataOutput, @Return Object param, @Duration long time) {
        depth = 0;
        if (depth == 0 && param != null) {
            println(" readObject: " + param.getClass().getName() + " - " + time);
        }
    }
}
