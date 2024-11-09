package info.ragozin.proflab.hzbench.demo;

import java.io.FileNotFoundException;

public class HzAutoNode {

    public static void main(String... args) throws FileNotFoundException {
        String node = System.getenv("HZNODE");
        System.out.println("Run as " + node);
        if (node != null) {
            switch (node) {
                case "HzNode1" -> new HzNode1().run();
                case "HzNode2" -> new HzNode2().run();
                case "HzNode3" -> new HzNode3().run();
                case "HzService" -> new HzService().run();
                default -> System.err.println("Unsupported $HZNODE value [" + node + "]");
            }
        }
    }
}
