package serverLogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import requestLogic.StatusRequest;
import requestLogic.StatusRequestBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class DatagramServerConnection implements ServerConnection {
    private final int BUFFER_SIZE = 1024;
    private static final Logger logger = LogManager.getLogger("io.github.zerumi.lab6");
    private final int port;

    protected DatagramServerConnection(int port) {
        this.port = port;
    }

    public StatusRequest listenAndGetData() {
        byte[] buffer = new byte[BUFFER_SIZE];
        try (DatagramSocket ds = new DatagramSocket(port)) {
            DatagramPacket dp;
            dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);

            logger.trace("Received connection.");
            logger.trace("Bytes: " + Arrays.toString(dp.getData()));

            return StatusRequestBuilder.initialize().setObjectStream(new ByteArrayInputStream(dp.getData())).setCallerBack(dp.getAddress(), dp.getPort()).setCode(200).build();
        } catch (IOException e) {
            logger.error("Something went wrong during I/O.");
        }
        return StatusRequestBuilder.initialize().setCode(-501).build();
    }

    @Override
    public void sendData(byte[] data, InetAddress addr) {
        try (DatagramSocket ds = new DatagramSocket(port)) {
            DatagramPacket dpToSend = new DatagramPacket(data, data.length, addr, port);
            ds.send(dpToSend);
        } catch (IOException ex) {
            logger.error("Something went wrong during I/O.");
        }
    }
}