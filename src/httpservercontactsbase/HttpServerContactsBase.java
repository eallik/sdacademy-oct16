package httpservercontactsbase;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;

public class HttpServerContactsBase {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/list",   new ListHandler());
        server.createContext("/new",    new NewHandler());
        server.createContext("/create", new CreateHandler());

        server.setExecutor(null);  // creates a default executor
        server.start();
    }

    static class NewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Content-Type", "text/html");

            writeResponse(t,
                    "<html><body>" +
                            "<form action=\"/create\" method=\"post\">" +
                            "<input name=\"name\" placeholder=\"Name\"><br>" +
                            "<input name=\"phone\" placeholder=\"Phone\"><br>" +
                            "<input type=\"submit\">" +
                            "</form>" +
                            "</body></html>"
            );
        }
    }

    static class CreateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(t.getRequestBody()));

            // TODO: interpret parameters sent from browser
            System.out.println(br.readLine());

            br.close();

            writeResponse(t, new Date().toString());
        }
    }

    static class ListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // TODO: send all contacts to browser
            writeResponse(t, new Date().toString());
        }
    }

    private static void writeResponse(HttpExchange t, String r) throws IOException {
        t.sendResponseHeaders(200, r.length());
        OutputStream os = t.getResponseBody();
        os.write(r.getBytes());
        os.close();
    }
}
