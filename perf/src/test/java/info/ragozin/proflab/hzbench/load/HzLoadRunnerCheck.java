package info.ragozin.proflab.hzbench.load;

import org.junit.Test;

import info.ragozin.proflab.hzbench.demo.HzService;

import java.io.FileNotFoundException;

public class HzLoadRunnerCheck {

    @Test
    public void runLoad() throws FileNotFoundException {
        HzLoadRunner runner = new HzLoadRunner(HzService.client());
        runner.run();
    }
}
