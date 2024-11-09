package info.ragozin.proflab.hzbench.demo;

public interface ProcNode {

    String getProcessTag();

    default int servicePort() { return -1; }

    boolean check();

    default boolean waitForProcess(int timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (deadline > System.currentTimeMillis()) {
            Thread.sleep(500);
            if (check()) {
                return  true;
            }
        }
        return check();
    }

    void kill();
}
