package commandManager.commands;

import exceptions.WrongAmountOfArgumentsException;
import main.Utilities;
import serverLogic.ServerConnectionHandler;
import serverLogic.ServerConnectionUtils;

/**
 * Shows count of the elements greater than distance value.
 *
 * @since 1.0
 * @author Zerumi
 */
public class CountGreaterThanDistanceCommand implements BaseCommand{
    @Override
    public void execute(String[] args) throws WrongAmountOfArgumentsException {
        Utilities.checkArgumentsOrThrow(args.length, 1);

        if (Utilities.isNotNumeric(args[1])) {
            System.out.println("Provided argument \"" + args[1] + "\" is not a number! Try again.");
            return;
        } else if (args[1].contains(",")) {
            System.out.println("Distance field cannot accept decimal values. Try again");
            return;
        }

        ServerConnectionUtils.sendCommand(this, args, ServerConnectionHandler.getCurrentConnection());
    }
}
