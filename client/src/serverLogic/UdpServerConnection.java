package serverLogic;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class UdpServerConnection implements ServerConnection {

    private static final Logger logger = LogManager.getLogger("io.github.zerumi.lab6");
    final DatagramChannel channel;
    SocketAddress address;

    protected UdpServerConnection(DatagramChannel channel, SocketAddress address) {
        this.channel = channel;
        this.address = address;
    }


    /**
     * Method for open a connection.
     */
    @Override
    public void openConnection() throws IOException {
        if (!channel.isConnected()) {
            channel.connect(address);
            logger.log(Level.INFO, "Opened channel to: " + address);
        }
    }

    /**
     * Method for close a connection
     */
    @Override
    public void closeConnection() throws IOException {
        if (channel.isConnected() && channel.isOpen()) {
            try {
                channel.disconnect();
                channel.close();
            } catch (IOException e) {
                channel.close();
            }
        }
    }

    @Override
    public ByteArrayInputStream sendData(byte[] bytesToSend) throws IOException {
        ByteArrayInputStream bos = null;
        if (channel.isConnected() && channel.isOpen()) {
            var buf = ByteBuffer.wrap(bytesToSend);
            channel.send(buf, address);
            bos = listenServer(); // TODO: wait 5 seconds
            // else: Server is not available
        } else this.openConnection();
        return bos;
    }

    private ByteArrayInputStream listenServer() throws IOException {
        ByteArrayInputStream res = null;
        if (channel.isConnected() && channel.isOpen()) {
            ByteBuffer buf = ByteBuffer.allocate(4096);
            address = channel.receive(buf);
            logger.debug("response read");
            logger.trace("bytes: " + Arrays.toString(buf.array()));
            res = new ByteArrayInputStream(buf.array());
        } else this.openConnection();
        return res;
    }
}
