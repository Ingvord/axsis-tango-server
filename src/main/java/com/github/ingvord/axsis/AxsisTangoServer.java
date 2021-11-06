package com.github.ingvord.axsis;

import magix.SseMagixClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.tango.server.ServerManager;

import javax.ws.rs.client.Client;
import java.util.Arrays;

public class AxsisTangoServer {

    private static final SseMagixClient MAGIX;
    public static final String MAGIX_CHANNEL = "axsis-xes";
    public static final String MAGIX_TARGET = "axsis";
    public static final String MAGIX_ORIGIN = "axsis-tango";

    static {
        Client client = ResteasyClientBuilder.newClient();

        MAGIX = new SseMagixClient("http://" + System.getProperty("MAGIX_HOST", "localhost:8080"), client);
    }

    public static void main(String[] args) {
        MAGIX.connect();

        ServerManager.getInstance().addClass("AxsisController", AxsisController.class);

        ServerManager.getInstance().start(args, AxsisController.class);
    }

    public static SseMagixClient getMagixClient(){
        return MAGIX;
    }
}
