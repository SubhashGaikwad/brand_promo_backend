package com.techvg.eoffice.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Pdf {

    /**
     * Generates PDF using Chrome Headless. Requires Chrome 58+ installed on system.
     * On windows, Chrome needs to be added as a PATH variable
     *
     * @throws IOException
     */
    public synchronized boolean createPdfUsingChrome(
        String htmlString,
        File pdfOutPutFile,
        String PDF_OUTPUT_CHROME,
        String source_file_path
    ) throws IOException {
        File htmlInputFile = new File(source_file_path);

        if (htmlInputFile.exists()) {
            htmlInputFile.delete();
        }
        if (pdfOutPutFile.exists()) {
            pdfOutPutFile.delete();
        }
        try {
            htmlInputFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedWriter bufferedWriter = null;

        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlInputFile), StandardCharsets.UTF_8));

        //bufferedWriter = new BufferedWriter(new FileWriterWithEncoding(htmlInputFile, "UTF-8"));
        bufferedWriter.write(htmlString);
        bufferedWriter.close();

        Runtime
            .getRuntime()
            .exec(
                "chrome --headless --disable-gpu --print-to-pdf=" + pdfOutPutFile.getAbsolutePath() + " " + htmlInputFile.getAbsolutePath()
            );

        System.out.println("PDF successfully prepared using Chrome");
        return true;
    }
}
