// This is BTrace 1 script, will not run with BTrace2

import static org.openjdk.btrace.core.BTraceUtils.println;

import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.Duration;
import org.openjdk.btrace.core.annotations.Kind;
import org.openjdk.btrace.core.annotations.Location;
import org.openjdk.btrace.core.annotations.OnMethod;
import org.openjdk.btrace.core.annotations.OnTimer;
import org.openjdk.btrace.core.annotations.ProbeClassName;
import org.openjdk.btrace.core.annotations.ProbeMethodName;
import org.openjdk.btrace.core.annotations.TargetInstance;
import org.openjdk.btrace.core.annotations.TargetMethodOrField;
import org.openjdk.btrace.core.annotations.Where;

@BTrace
public class TestProbe {

//    @OnMethod(
//            clazz="com.hazelcast.mapreduce.impl.task.JobSupervisor",
//            method="getReducerAddressByKey"
//            )
//        public static void entryJobS(@ProbeMethodName(fqn=false) String probeMethod) {
//            println(probeMethod);
//        }

//    @OnMethod(clazz = "/com\\.hazelcast\\.mapreduce\\.impl\\.task\\..*/", method = "/.*/",
//            location = @Location(value = Kind.CALL, clazz = "/java\\.util\\.concurrent\\..*ConcurrentMap/", method = "/get/", where=Where.BEFORE))
//    public static void enterMapGet(@TargetMethodOrField(fqn = true) String targetMethod) {
//        println(targetMethod);
//    }
//
//    @OnMethod(clazz = "/com\\.hazelcast\\.mapreduce\\.impl\\.task\\..*/", method = "/.*/",
//            location = @Location(value = Kind.CALL, clazz = "/java\\.util\\.concurrent\\..*ConcurrentMap/", method = "/get/", where=Where.AFTER))
//    public static void leaveMapGet(@TargetMethodOrField(fqn = true) String targetMethod, @Duration long duration) {
//        println(targetMethod + " - " + duration);
//    }

    @OnMethod(clazz="+com.hazelcast.internal.serialization.impl.StreamSerializerAdapter", method="/read.*/", location=@Location(value=Kind.RETURN))
    public static void read(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(probeClass + "::" + probeMethod + " - " + duration);
    }

        @OnTimer(5000)
        public static void timer() {
            println("Timer trigger");
        }
}
