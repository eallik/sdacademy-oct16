package httpservertimezone;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServerTimeZone {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MainHandler());
        server.createContext("/time", new TimeHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class TimeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().getPath().replace("/time/", "");
            ZoneId zoneId = null;
            try { zoneId = ZoneId.of(uri); }
            catch(final Exception e) {
                String response = "<html><center><br><br><h1>No such zone!</h1><br><h2><a href='/'>Back</a></h2></center></html>";
                writeResponse(t, response);
                return;
            }
            System.out.println("Zone:" + zoneId + "-" + uri);
            LocalDateTime localDateTime = LocalDateTime.now();
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);

            writeResponse(t,
                    "<html><center><br><br><h1>" + zonedDateTime.toString() + "</h1></center></html>"
            );
        }
    }

    static class MainHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            StringBuilder response = new StringBuilder();

            response.append("<html><h2>List of avalaible timezones</h2><br>");
            ZoneId.getAvailableZoneIds().stream().sorted().forEach(x -> {
                response.append("<a href='time/" + x + "'>" + x + "</a><br>");
            });
            response.append("</html>");

            writeResponse(t, response.toString());
        }
    }

    private static void writeResponse(HttpExchange t, String r) throws IOException {
        t.sendResponseHeaders(200, r.length());
        OutputStream os = t.getResponseBody();
        os.write(r.getBytes());
        os.close();
    }
}
