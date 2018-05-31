package com.pradeeprizal.ask.farmersmkt.functional;

import com.pradeeprizal.ask.farmersmkt.FarmersMarketStreamHandler;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URL;

/** Simulates a lambda invokation. **/
public class HandlersFunctionalTests {
    public static String WHICH_MARKET_INPUT_FILE = "com/pradeeprizal/ask/farmersmkt/functional/whichmarket.json";
    public static String DESCRIBE_MARKET_INPUT_FILE = "com/pradeeprizal/ask/farmersmkt/functional/describemarket.json";

    @Test
    public void testWhichMarket() throws IOException {
        FarmersMarketStreamHandler handler = new FarmersMarketStreamHandler();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(WHICH_MARKET_INPUT_FILE);

        File fstream = File.createTempFile("tmp",".json");
        FileOutputStream fostream = new FileOutputStream(fstream);

        handler.handleRequest(inputStream, fostream, null);
        String result = FileUtils.readFileToString(fstream, Charsets.toCharset("utf-8"));
        System.out.println(result);
        fstream.deleteOnExit();

        // TODO: Add assertion, using object mapper, read the output, and assert that response has correct text.
    }
}
