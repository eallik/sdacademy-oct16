import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {
    public static void main(final String[] args) throws Exception {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MainHandler());
        server.createContext("/getTime", new MyDateHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyDateHandler implements HttpHandler {
        @Override
        public void handle(final HttpExchange t) throws IOException {
            final String uri = t.getRequestURI().getPath().replace("/getTime/", "");
            final ZoneId zoneId = null;
            try {
                zoneId = ZoneId.of(uri);
            } catch(final Exception e) {
                final String response = "<html><center><br><br><h1>No such zone!</h1><br><h2><a href='/'>Back</a></h2></center></html>";
                writeResponse(t, response);
                return;
            }
            System.out.println("Zone:" + zoneId + "-" + uri);
            final LocalDateTime localDateTime = LocalDateTime.now();
            final ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);

            final String response = ("<html><center><br><br><h1>" +
                                     zonedDateTime.toString() +
                                     "</h1></center></html>");
            writeResponse(t, response);
        }
    }

    static class MainHandler implements HttpHandler {
        @Override
        public void handle(final HttpExchange t) throws IOException {
            final StringBuilder response = new StringBuilder("<html><h2>List of avalaible timezones</h2><br>");
            ZoneId.getAvailableZoneIds()
                    .stream()
                    .sorted()
                    .forEach(x -> response.append("<a href='getTime/" + x + "'>" + x + "</a><br>"));
            response.append("</html>");

            writeResponse(t, response.toString());
        }
    }

    private static void writeResponse(HttpExchange t, String r) {
        t.sendResponseHeaders(200, response.length());
        final OutputStream os = t.getResponseBody();
        os.write(r.getBytes());
        os.close();
    }
}
