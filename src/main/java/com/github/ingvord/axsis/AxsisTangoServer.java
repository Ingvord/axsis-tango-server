package com.github.ingvord.axsis;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.attach.ElasticApmAttacher;
import magix.SseMagixClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.tango.server.ServerManager;

import javax.ws.rs.client.Client;
import java.util.Arrays;

public class AxsisTangoServer {

    private static final SseMagixClient MAGIX;
    public static final String MAGIX_CHANNEL = "axsis-xes";
    public static final String MAGIX_TARGET = "axsis";
    public static final String MAGIX_ORIGIN = "axsis-tango";

    static {
        ResteasyJackson2Provider jacksonProvider = new ResteasyJackson2Provider();

        Client client = ResteasyClientBuilder.newClient().register(jacksonProvider);

        MAGIX = new SseMagixClient("http://" + System.getenv("MAGIX_HOST"), client);
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
