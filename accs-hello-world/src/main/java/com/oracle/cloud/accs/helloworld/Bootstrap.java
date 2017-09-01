package com.oracle.cloud.accs.helloworld;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;


public class Bootstrap {

    public static void main(String[] args) throws IOException {
        
        String host = System.getenv().getOrDefault("HOSTNAME", "localhost");
        int port = Integer.valueOf(System.getenv().getOrDefault("PORT", "9090"));
        
        HttpServer server = HttpServer.create(new InetSocketAddress(host,port), 0);
        
        HttpContext context = server.createContext("/");
        context.setHandler((he) -> {
            he.sendResponseHeaders(200, 0);
            final OutputStream output = he.getResponseBody();
            String address = he.getRemoteAddress().getAddress().getHostAddress();
            output.write(("Hello @ "+ new Date().toString() + " from "+ address).getBytes());
            output.flush();
            he.close();
        });
        
        server.start();
        System.out.println("server started");
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.stop(0);
            }
        });

       
    }
}
