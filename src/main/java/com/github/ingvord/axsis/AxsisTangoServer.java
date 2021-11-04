package com.github.ingvord.axsis;

import org.tango.server.ServerManager;

public class AxsisTangoServer {
    public static void main(String[] args) {
        ServerManager.getInstance().addClass("AxsisController", AxsisController.class);

        ServerManager.getInstance().start(new String[]{"virtual"}, AxsisTangoServer.class);
    }
}
