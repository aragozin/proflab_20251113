import static org.openjdk.btrace.core.BTraceUtils.println;

import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.Duration;
import org.openjdk.btrace.core.annotations.Kind;
import org.openjdk.btrace.core.annotations.Location;
import org.openjdk.btrace.core.annotations.OnMethod;
import org.openjdk.btrace.core.annotations.OnTimer;
import org.openjdk.btrace.core.annotations.ProbeClassName;
import org.openjdk.btrace.core.annotations.ProbeMethodName;
import org.openjdk.btrace.core.annotations.Where;
import org.openjdk.btrace.core.types.AnyType;

@BTrace
public class WebTracer {

    @OnMethod(clazz="+org.thymeleaf.spring4.view.ThymeleafView", method="render", location=@Location(value=Kind.RETURN))
    public static void viewRender(AnyType a1, AnyType a2, AnyType a3, @ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(probeClass + "::" + probeMethod + " - " + duration);
    }

    @OnMethod(clazz="+org.thymeleaf.spring4.view.ThymeleafView", method="renderFragment", location=@Location(value=Kind.RETURN))
    public static void viewRenderFragment(AnyType a1, AnyType a2, AnyType a3, AnyType a4, @ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(probeClass + "::" + probeMethod + " - " + duration);
    }

    @OnMethod(clazz="+org.thymeleaf.spring4.view.ThymeleafView", method="renderFragment", location=@Location(value=Kind.CALL, clazz="/.*/", method="process", where = Where.BEFORE))
    public static void callTemplateProcess(String template, AnyType context, @ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(probeClass + "::" + probeMethod + " - " + duration);
    }


    @OnMethod(clazz="/.*TemplateEngine/", method="process", location=@Location(value=Kind.RETURN))
    public static void templateLeave(String template, AnyType context, @ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(probeClass + "::" + probeMethod + " - " + duration);
    }

    @OnMethod(clazz="/.*TemplateEngine/", method="process", location=@Location(value=Kind.RETURN))
    public static void templateLeave(String template, AnyType templateSelectors, AnyType context, @ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(probeClass + "::" + probeMethod + " - " + duration);
    }

    @OnTimer(5000)
    public static void timer() {
        println("Timer trigger");
    }
}
