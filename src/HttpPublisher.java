import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class HttpPublisher {

    private final SystemCounters systemCounters;

    public HttpPublisher(int port, SystemCounters systemCounters) throws IOException {
        this.systemCounters = systemCounters;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
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
            String response = systemCounters.generateStatsReport().toString();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}