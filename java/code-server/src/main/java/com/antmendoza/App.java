package com.antmendoza;

import com.google.common.net.HttpHeaders;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.temporal.api.common.v1.Payload;
import io.temporal.api.common.v1.Payloads;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        startServer();
    }

    private static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        server.createContext("/decode", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            try {

                Payloads.Builder incomingPayloads = Payloads.newBuilder();
                try (InputStreamReader ioReader = new InputStreamReader(exchange.getRequestBody())) {
                    AbstractRemoteDataEncoderCodec.JSON_FORMAT.merge(ioReader, incomingPayloads);
                } catch (IOException e) {
//                    exchange.sendResponseHeaders(HttpServletResponse.SC_BAD_REQUEST, -1);
                    return;
                }

                List<Payload> incomingPayloadsList = incomingPayloads.build().getPayloadsList();


                List<Payload> outgoingPayloadsList = new CryptCodec().decode(incomingPayloadsList);


                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");



                exchange.getRequestHeaders().set(HttpHeaders.CONTENT_TYPE, AbstractRemoteDataEncoderCodec.CONTENT_TYPE_APPLICATION_JSON);


                OutputStream os = exchange.getResponseBody();
                try (OutputStreamWriter out = new OutputStreamWriter(os)) {
                    AbstractRemoteDataEncoderCodec.JSON_PRINTER.appendTo(
                            Payloads.newBuilder().addAllPayloads(outgoingPayloadsList).build(), out);
                }


                os.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }

        }
    }


}


