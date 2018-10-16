import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Date;

public class Main {
    public static void main(final String[] args) throws Exception {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/list",   new ListHandler());
        server.createContext("/new",    new NewHandler());
        server.createContext("/create", new CreateHandler());

        server.setExecutor(null);  // creates a default executor

        server.start();
    }


    static class NewHandler implements HttpHandler {
        @Override
        public void handle(final HttpExchange t) throws IOException {
            final String response = (
                    "<html>" +
                    "<body>" +
                    "<form action=\"/create\" method=\"post\">" +
                    "<input name=\"name\" placeholder=\"Name\"><br>" +
                    "<input name=\"phone\" placeholder=\"Phone\"><br>" +
                    "<input type=\"submit\">" +
                    "</form>" +
                    "</body>" +
                    "</html>"
            );

            t.getResponseHeaders().add("Content-Type", "text/html");
            t.sendResponseHeaders(200, response.length());

            final OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class CreateHandler implements HttpHandler {
        @Override
        public void handle(final HttpExchange t) throws IOException {
            final BufferedReader br = new BufferedReader(new InputStreamReader(t.getRequestBody()));
    
            // TODO here interpret parameters sent from the browser
            System.out.println(br.readLine());

            br.close();
            
            final String response = new Date().toString();
            t.sendResponseHeaders(200, response.length());

            final OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ListHandler implements HttpHandler {
        @Override
        public void handle(final HttpExchange t) throws IOException {
            // TODO here send all contacts to the browser
            final String response = new Date().toString();
            t.sendResponseHeaders(200, response.length());
            final OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
