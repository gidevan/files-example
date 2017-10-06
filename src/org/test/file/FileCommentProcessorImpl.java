package org.test.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileCommentProcessorImpl implements FileCommentProcessor {

    private static final String START_COMMENT_SYMBOL = "/*";
    private static final String END_COMMENT_SYMBOL = "*/";
    private static final String OUT_DIR = "out/";

    @Override
    public void processFile(String fileName) {
        Path inPath = Paths.get(fileName);
        String fn = inPath.getFileName().toString();
        String outFileName = OUT_DIR + fn;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFileName), Charset.forName("UTF-8"))) {
            try(BufferedReader reader = Files.newBufferedReader(inPath, Charset.forName("UTF-8"))) {
                List<String> buffer = new ArrayList<>();
                String line = null;
                while((line = reader.readLine()) != null) {
                    if(buffer.isEmpty()) {
                        String newLine = removeCommentsFromLine(line);
                        if(newLine.contains(START_COMMENT_SYMBOL)) {
                            buffer.add(newLine);
                        } else {
                            writer.write(newLine + "\n");
                        }
                    } else if(line.contains(END_COMMENT_SYMBOL) && !buffer.isEmpty()){
                        buffer.add(line);
                        writeLines(writer, buffer);
                    }

                }
                if(!buffer.isEmpty()) {
                    String lastLineValue = buffer.get(0);
                    String lastLine = lastLineValue.substring(0, lastLineValue.indexOf(START_COMMENT_SYMBOL));
                    writer.write(lastLine);
                }
            } catch (IOException e) {
                System.out.println("exception during processing file: [" + fileName + "]");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("exception duiring writing in the file file: [" + outFileName + "]");
            e.printStackTrace();
        }


    }

    private void writeLines(BufferedWriter writer, List<String> buffer) throws IOException {
        String firstCommentRow = buffer.get(0);
        String lastCommentRow = buffer.get(buffer.size() - 1);

        String firstRow = firstCommentRow.substring(0, firstCommentRow.indexOf(START_COMMENT_SYMBOL));
        int lastRowIndex = lastCommentRow.indexOf(END_COMMENT_SYMBOL);
        String lastRow = lastCommentRow.substring(lastRowIndex + END_COMMENT_SYMBOL.length());
        writer.write(firstRow + "\n");
        writer.write(lastRow + "\n");
        buffer.clear();
    }

    private String removeCommentsFromLine(String line) {
        String newLine = line;
        int startCommentIndex = newLine.indexOf(START_COMMENT_SYMBOL);
        int endCommentIndex = newLine.indexOf(END_COMMENT_SYMBOL, startCommentIndex + 2);
        while(endCommentIndex > 0) {
            if(startCommentIndex >= 0 && endCommentIndex > startCommentIndex + 1) {
                String commentSubString = newLine.substring(startCommentIndex, endCommentIndex + 2);
                newLine = newLine.replace(commentSubString, "");
            }
            startCommentIndex = newLine.indexOf(START_COMMENT_SYMBOL, startCommentIndex+1);
            endCommentIndex = newLine.indexOf(END_COMMENT_SYMBOL, startCommentIndex + 2);
        }

        return newLine;
    }
}
