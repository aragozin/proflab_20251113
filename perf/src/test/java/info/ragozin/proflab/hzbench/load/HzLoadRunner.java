package info.ragozin.proflab.hzbench.load;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import info.ragozin.labconsole.agent.DemoInitializer;
import info.ragozin.perflab.hazelagg.Client;
import info.ragozin.perflab.hazelagg.SliceKey;

public class HzLoadRunner {

    private final Client client;
    private final List<String> books;

    public HzLoadRunner(Client client) {
        this.client = client;
        this.books = new ArrayList<>(client.books());
    }

    public void run() throws FileNotFoundException {
        Random rnd = new Random(1);
        int count = 0;
        double total = 0;
        PrintWriter reporter = openReportFile();
        while(true) {
            String book = books.get(rnd.nextInt(books.size()));
            long ts = System.nanoTime();
            Map<SliceKey, BigDecimal> result = client.snapshotBook(book);
            double timeMS = 1d * (System.nanoTime() - ts) / TimeUnit.MILLISECONDS.toNanos(1);

            System.out.println("Book [" + book + "], positions: " + result.size() + String.format(", snapshot time: %.2fms", timeMS));

            count++;
            total += timeMS;

            if (count == 20) {
                double avgMs = total / count;
                String rollup = String.format("Average over last %d calls is %.2fms", count, avgMs);
                System.out.println("\n" + rollup + "\n");
                reporter.println(rollup);
                reporter.flush();
                count = 0;
                total = 0;
            }
        }
    }

    private PrintWriter openReportFile() throws FileNotFoundException {
        String path = DemoInitializer.prop("hzdemo.loadreport.name", "report.txt");
        File rfile = new File(path);
        if (rfile.getParentFile() != null) {
            rfile.getParentFile().mkdirs();
        }
        rename(rfile);
        OutputStream os = new FileOutputStream(rfile);
        return new PrintWriter(os, true);
    }

    private void rename(File file) {
        int n = 0;
        while(file.exists()) {
            String name = file.getName();
            String nname;
            if (name.indexOf('.') >= 0) {
                int ch = name.lastIndexOf('.');
                nname = name.substring(0, ch) + "." + n + name.substring(ch);
            } else {
                nname = name + "." + n;
            }
            File tfile = new File(file.getParentFile(), nname);
            if (!tfile.exists()) {
                file.renameTo(tfile);
            }
            ++n;
        }
    }
}
