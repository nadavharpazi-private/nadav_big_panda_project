package big_panda_project;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

// runs the http that publish all current statistics

class HttpPublisher {

    private final SystemCounters systemCounters;

    public HttpPublisher(int port, SystemCounters systemCounters) throws IOException {
        this.systemCounters = systemCounters;
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            Logger.sendLog(Globals.error, ("http server failed to listen to port: " + port + " try another port.."));
            throw e;
        }
        server.createContext("/stats", new requestHandler());
        server.setExecutor(null); // creates a default executor
        Logger.sendLog(Globals.info, ("starting http server on port: " + port));
        server.start();
        Logger.sendLog(Globals.info, ("http server listens on port: " + port));
    }


    private class requestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Logger.sendLog(Globals.verbose, ("http server: sending a response"));
            String response = systemCounters.getStatsReport();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}