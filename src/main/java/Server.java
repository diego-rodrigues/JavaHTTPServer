import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    static Logger logger;

    public static void configureLogger() {
        logger = LogManager.getRootLogger();
    }

    public static void main(String[] args) throws IOException {
        configureLogger();
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/hello", new HelloWorldHandler());
        server.setExecutor(threadPoolExecutor);
        logger.info("Server started at port 8001");
        server.start();
    }
}
