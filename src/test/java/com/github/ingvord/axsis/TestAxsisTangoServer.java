package com.github.ingvord.axsis;

import fr.esrf.Tango.DevVarDoubleStringArray;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.client.ez.proxy.TangoProxies;
import org.tango.client.ez.proxy.TangoProxy;

public class TestAxsisTangoServer {

    @Test
    @Ignore
    public void test() throws Exception{
        TangoProxy proxy = TangoProxies.newDeviceProxyWrapper("tango://localhost:10000/virtual/axsis/1");

        for(var i = 0; i<10; i++)
            proxy.executeCommand("move", new DevVarDoubleStringArray(new double[]{Math.random()}, new String[]{"1"}));


        Thread.sleep(1000);
    }
}
