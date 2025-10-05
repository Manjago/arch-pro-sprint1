package io.github.manjago.temperatureapi;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8081), 5);
        server.createContext("/temperature/", new TemperatureByIdHandler());
        server.createContext("/temperature", new TemperatureByLocationHandler());

        final ExecutorService executor = Executors.newFixedThreadPool(2);
        server.setExecutor(executor);
        server.start();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
    }
}
