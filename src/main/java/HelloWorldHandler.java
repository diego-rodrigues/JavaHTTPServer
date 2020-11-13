import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class HelloWorldHandler implements HttpHandler {

    static Logger logger;

    public HelloWorldHandler() {
        configureLogger();
    }

    public static void configureLogger() {
        logger = LogManager.getRootLogger();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if ("GET".equals(httpExchange.getRequestMethod())) {
            logger.info("HELLO WORLD REQUESTED!");

            String param = getParameter(httpExchange);
            handleResponse(httpExchange, param);
        } else
            logger.error("Unsupported request type.");
    }

    private String getParameter(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String parameter) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Hello " + parameter)
                .append("</h1>")
                .append("</body>")
                .append("</html>");

        String htmlResponse = htmlBuilder.toString();

        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
