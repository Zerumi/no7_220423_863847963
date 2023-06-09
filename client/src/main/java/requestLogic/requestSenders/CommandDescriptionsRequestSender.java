package requestLogic.requestSenders;

import commandLogic.CommandDescription;
import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import requests.CommandDescriptionsRequest;
import responses.CommandDescriptionsResponse;
import serverLogic.ServerConnectionHandler;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.ArrayList;

public class CommandDescriptionsRequestSender {

    private static final Logger logger = LogManager.getLogger("io.github.zerumi.lab6");

    public ArrayList<CommandDescription> sendRequestAndGetCommands() {
        ArrayList<CommandDescription> result = null;

        CommandDescriptionsRequest request = new CommandDescriptionsRequest();

        try {
            CommandDescriptionsResponse response = (CommandDescriptionsResponse) new RequestSender().sendRequest(request, ServerConnectionHandler.getCurrentConnection());
            result = response.getCommands();
        } catch (PortUnreachableException e) {
            logger.fatal("Server is unavailable. Please, wait until server will came back.");
            logger.fatal("We can't get available commands because the server is unavailable.");
        } catch (GotAnErrorResponseException e) {
            logger.error("Received error from a server! " + e.getErrorResponse().getMsg());
        } catch (ProceedException e) {
            logger.error("We've lost some packets during getting response: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Something went wrong during I/O operations.");
        }

        return result;
    }
}
