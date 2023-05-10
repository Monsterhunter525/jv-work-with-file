package core.basesyntax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class WorkWithFile {
    public void getStatistic(String fromFileName, String toFileName) {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);
        int result;
        Handler handler = null;
        try {
            List<String> fileContent = readFile(fromFile);
            handler = handle(handler, fileContent);
            result = handler.supplies - handler.buys;
        } catch (IOException e) {
            throw new RuntimeException("Can't read this file", e);
        }
        writeFile(toFile, handler.supplies, handler.buys, result);
    }

    private static Handler handle(Handler handler, List<String> fileContent) {
        int supplies = 0;
        int buys = 0;
        for (String content : fileContent) {
            String action = content.split(",")[0];
            String sum = content.split(",")[1];
            if (action.equals("supply")) {
                supplies += Integer.parseInt(sum);
            }
            if (action.equals("buy")) {
                buys += Integer.parseInt(sum);
            }
            handler = new Handler(supplies, buys);
        }
        return handler;
    }

    private static class Handler {
        private final int supplies;
        private final int buys;

        public Handler(int supplies, int buys) {
            this.supplies = supplies;
            this.buys = buys;
        }
    }

    private static void writeFile(File toFile, int supplies, int buys, int result) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFile))) {
            String builder = "supply," + supplies + System.lineSeparator()
                    + "buy," + buys + System.lineSeparator()
                    + "result," + result + System.lineSeparator();
            bufferedWriter.write(builder);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data", e);
        }
    }

    private static List<String> readFile(File fromFile) throws IOException {
        return Files.readAllLines(fromFile.toPath());
    }
}
