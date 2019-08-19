package revolut;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */

public class RevolutApiServer {

    private static final Logger log = LoggerFactory.getLogger(RevolutApiServer.class);

    public static void main(String[] args) {
        Server server = new Server(8080);
        ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS); //stateless

        servletContextHandler.setContextPath("/");
        server.setHandler(servletContextHandler);

        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter(
                "jersey.config.server.provider.packages",
                "revolut"
        );

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            log.error("Error starting the Http Server", ex);
            System.exit(1);
        }

        finally {
            server.destroy();
        }
    }
}


