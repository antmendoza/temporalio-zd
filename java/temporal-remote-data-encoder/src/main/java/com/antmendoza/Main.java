package com.antmendoza;

import com.antmendoza.workflow.CryptCodec;
import io.temporal.rde.httpserver.RDEHttpServer;

import java.io.IOException;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        try {
            new RDEHttpServer(Collections.singletonList(new CryptCodec()), 8888).start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
