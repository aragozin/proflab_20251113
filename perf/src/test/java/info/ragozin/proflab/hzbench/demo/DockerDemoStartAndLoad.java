package info.ragozin.proflab.hzbench.demo;

import org.junit.Test;

import java.io.IOException;

public class DockerDemoStartAndLoad {

    @Test
    public void action() throws IOException, InterruptedException {
        new DockerDemoStart().startDemo();
        DemoActions.load();
    }
}
