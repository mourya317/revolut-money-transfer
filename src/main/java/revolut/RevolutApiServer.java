package revolut;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
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

        // Create JAX-RS application.
        final ResourceConfig application = new ResourceConfig()
                .packages("jersey.jetty.embedded","revolut")
                .register(JacksonFeature.class);

        ServletContextHandler context
                = new ServletContextHandler(NO_SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = new ServletHolder(new
                org.glassfish.jersey.servlet.ServletContainer(application));
        jerseyServlet.setInitOrder(0);

        context.addServlet(jerseyServlet, "/*");

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception ex) {
            log.error("Error starting the Http Server", ex);
            System.exit(1);
        }

        finally {
            jettyServer.destroy();
        }
    }
}


