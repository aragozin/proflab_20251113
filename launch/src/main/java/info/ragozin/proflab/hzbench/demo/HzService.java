package info.ragozin.proflab.hzbench.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.slf4j.bridge.SLF4JBridgeHandler;

import info.ragozin.labconsole.agent.DemoInitializer;
import info.ragozin.labconsole.agent.GenericStarter;
import info.ragozin.perflab.hazelagg.Client;
import info.ragozin.perflab.hazelagg.Node;
import info.ragozin.perflab.hazelagg.Server;
import info.ragozin.perflab.hazelagg.Service;

public class HzService extends GenericStarter implements ProcNode {

    private static final String TAG = "hzservice";
    private static final int DEFAULT_SERVICE_PORT = 8080;

    private Server server;

    public static Client client() {
        return new Client("http://127.0.0.1:" + httpPort());
    }

    public static int httpPort() {
        return DemoInitializer.propAsInt("hzdemo.http.port", DEFAULT_SERVICE_PORT);
    }

    @Override
    public int servicePort() {
        return httpPort();
    }

    public static HzService control() {
        return new HzService(true);
    }

    @Override
    public String getProcessTag() {
        return TAG;
    }

    protected HzService() {
        super();
    }

    protected HzService(boolean control) {
        super(control);
    }


    public static void main(String... args) throws FileNotFoundException {
        new HzService().run();
    }

    @Override
    protected void run() throws FileNotFoundException {

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger("").setLevel(Level.FINEST);

        Node node = new Node(DemoInitializer.file("gridapp/node-conf-lite.xml"));
        Service serv = new Service(node);
        server = new Server(serv);
        try {
        	server.start(httpPort());
        } catch (Exception e) {
        	// do not hang around
        	System.exit(1);
        }
    }

    public void waitHttp() {
        waitForHttp(httpPort());
    }

    public boolean checkPort() {
        return checkPort(httpPort());
    }

    @SuppressWarnings("resource")
    private static void waitForHttp(int port) {
        while(true) {
            if (!control().check()) {
                System.err.println("Startup failed, see logs in var/" + TAG + "/logs");
                throw new RuntimeException();
            }
            try {
                URL url = new URL("http://127.0.0.1:" + port + "/size");
                String text = IOUtils.toString(url.openStream());
                if (text != null && text.length() > 0) {
                    return;
                }
            }
            catch(IOException e) {
                // ignore;
            }
        }
    }

    @SuppressWarnings("resource")
    private static boolean checkPort(int port) {
        for (int i = 0; i != 2; ++i) {
            try {
                Socket sock = new Socket();
                sock.setSoTimeout(3);
                sock.connect(new InetSocketAddress("127.0.0.1", port));
                if (sock.isConnected()) {
                    sock.close();
                    return true;
                }
            }
            catch(IOException e) {
                // ignore;
            }
        }
        return false;
    }
}
