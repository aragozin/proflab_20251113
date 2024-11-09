package info.ragozin.proflab.hzbench.demo;

import info.ragozin.demostarter.ContainerHelper;
import info.ragozin.labconsole.agent.DemoInitializer;
import org.junit.Test;

import java.io.IOException;

public class DockerDemoStart {

    @Test
    public void startDemo() throws InterruptedException, IOException {

        DemoInitializer.initConfiguration();

        if (!ContainerHelper.isDockerAvailable()) {
            System.out.println("Docker command is not available, unable to start contenerized environbment");
            System.exit(1);
        }

        ContainerHelper.DockerNetwork hznet = ContainerHelper.network("hzbench");

        if (HzService.control().checkPort()) {
            System.out.println("Port " + HzService.httpPort() + " is active");
            if (!ContainerHelper.checkRunning(HzService.control().getProcessTag())) {
                System.out.println("Container is not running, try to kill host process");
                HzService.control().kill();
            }
        }

        startContainer(HzNode1.control(), hznet, 1 );
        startContainer(HzNode2.control(), hznet, 2);
        startContainer(HzNode3.control(), hznet, 3);
        startContainer(HzService.control(), hznet, 4);

        Thread.sleep(5000);

        System.out.println("Waiting for http://127.0.0.1:" + HzService.httpPort());
        HzService.control().waitHttp();

        DemoActions.generateData();

        System.out.println("");
        System.out.println("Demo cluster is started via");
        System.out.println("HzService endpoint is available at http://localhost:" + HzService.httpPort());
        System.out.println("");
        System.out.println("Remove \"pids\" directory to stop demo environment");
        System.out.println("Use \"docker rm -f hznode1 hznode2 hznode3 hzservice\" to remove containers");
        System.out.println("Use \"docker network rm hzbench\" to remove network bridge");
        System.out.println("");
        System.out.println("");
    }

    private void startContainer(ProcNode control, ContainerHelper.DockerNetwork hznet, int nodeNo) throws InterruptedException {
        if (control.check()) {
            if (!ContainerHelper.checkRunning(control.getProcessTag())) {
                System.out.println("Container is not running, stopping process ...");
                control.kill();
                Thread.sleep(3000);
            }
        } else {
            ContainerHelper.removeContainer(control.getProcessTag());
        }

        if (!ContainerHelper.checkRunning(control.getProcessTag())) {
            System.out.println("Starting container: " + control.getProcessTag());
            ContainerHelper.removeContainer(control.getProcessTag());

            ContainerHelper.Builder builder = ContainerHelper.builder(control.getProcessTag(), "hzbench-demo-launch:1.0.0-SNAPSHOT")
                    .memory(300)
                    .network(hznet)
                    .env("HZNODE", control.getClass().getSimpleName())
                    .mount("pids", "/app/pids")
                    .mount("var", "/app/var")
                    .jvmArg("-DlifeGrant.port=" + (23045 + nodeNo))
                    .port(23045 + nodeNo, 23045 + nodeNo);

            if (control.servicePort() > 0) {
                builder.port(control.servicePort(), control.servicePort());
            }

            if (builder.run()) {
                System.out.println("Waiting for container");
                control.waitForProcess(5000);
                if (!ContainerHelper.checkRunning(control.getProcessTag())) {
                    System.out.println();
                    System.out.println("Container " + control.getProcessTag() + " has failed to start, for logs run");
                    System.out.println();
                    System.out.println("    docker logs " + control.getProcessTag());
                    System.exit(1);
                }
            }
        } else {
            System.out.println("Container is already running: " + control.getProcessTag());
        }
    }
}
