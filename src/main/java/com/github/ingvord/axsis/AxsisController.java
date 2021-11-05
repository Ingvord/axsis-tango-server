package com.github.ingvord.axsis;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import io.reactivex.Observable;
import magix.Message;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.DeviceState;
import org.tango.server.annotation.*;
import org.tango.server.device.DeviceManager;
import org.tango.utils.DevFailedUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Device(transactionType = TransactionType.NONE)
public class AxsisController {

    private final Logger logger = LoggerFactory.getLogger(AxsisController.class);

    private String host;
    private String port;

    @DeviceManagement
    private DeviceManager manager;

    @Init
    @StateMachine(endState =  DeviceState.ON)
    public void init() throws DevFailed {
        String hostPort;
        if(manager.getName().endsWith("1")){
            hostPort = System.getProperty("CTRL1");
        } else if(manager.getName().endsWith("2")){
            hostPort = System.getProperty("CTRL2");
        } else {
            throw DevFailedUtils.newDevFailed(String.format("Invalid configuration: unknown device %s", manager.getName()));
        }

        host = hostPort.split(":")[0];
        port = hostPort.split(":")[1];
    }

    @Attribute
    public String getHost(){
        return host;
    }

    @Attribute
    public String getPort(){
        return port;
    }

    @Command
    public void move(DevVarDoubleStringArray values){
        AxsisTangoServer.getMagixClient().broadcast(AxsisTangoServer.MAGIX_CHANNEL,
                Message.builder()
                        .setId(System.currentTimeMillis())
                        .setOrigin(AxsisTangoServer.MAGIX_ORIGIN)
                        .setTarget(AxsisTangoServer.MAGIX_TARGET)
                        .addPayload(new AxsisMessage()
                            .withIp(this.host)
                            .withPort(this.port)
                            .withAction("MOV")
                            .withValue(
                                    Observable.zip(
                                        Observable.fromArray(values.svalue),
                                        Observable.fromArray(DoubleStream.of(values.dvalue).boxed().toArray(Double[]::new)),
                                        Map::entry
                                    ).toMap(Map.Entry::getKey, Map.Entry::getValue).blockingGet()
                            )
                        )
                        .build());
    }

    public void setManager(DeviceManager manager){
        this.manager = manager;
    }
}
