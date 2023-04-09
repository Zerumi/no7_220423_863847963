package commandLogic.commandReceiverLogic;

import commandLogic.commandReceiverLogic.handlers.ReceiverHandler;
import commandLogic.commandReceiverLogic.receivers.ExternalBaseReceiver;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ReceiverManager {

    private static ReceiverManager instance;
    private final LinkedHashMap<ReceiverType, ReceiverHandler> receivers;

    {
        receivers = new LinkedHashMap<>();
    }

    public static ReceiverManager getInstance() {

        if (instance == null)
            instance = new ReceiverManager();

        return instance;
    }

    public void registerHandler(ReceiverType type, ReceiverHandler handler) {
        receivers.put(type, handler);
    }

    public void registerReceiver(ReceiverType type, ExternalBaseReceiver receiver) {
        receivers.get(type).addReceiver(receiver);
    }

    public ArrayList<? extends ExternalBaseReceiver> getReceivers(ReceiverType type) {
        return receivers.get(type).getReceivers();
    }
}
