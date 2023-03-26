package commandManager.commands;

import commandManager.commandResponse.CommandResponse;
import models.Route;
import models.comparators.RouteDistanceComparator;
import models.handlers.CollectionHandler;
import models.handlers.ModuleHandler;
import models.handlers.RouteNetworkHandler;
import models.handlers.RoutesHandler;

import java.util.HashSet;

/**
 * Removes elements from collection greater than given in argument.
 *
 * @author Zerumi
 * @since 1.0
 */
public class RemoveGreaterCommand implements BaseCommand {
    CommandResponse response;
    ModuleHandler<Route> handler;

    /**
     * Default constructor with handler from 1.0
     */
    public RemoveGreaterCommand() {
        handler = new RouteNetworkHandler();
    }

    /**
     * Provides choosing handler
     *
     * @param handler ModuleHandler for operating
     * @since 1.1
     */
    public RemoveGreaterCommand(ModuleHandler<Route> handler) {
        this.handler = handler;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescr() {
        return "Removes elements from collection greater than given in argument. Comparing is set by distance.";
    }

    @Override
    public String getArgs() {
        return "{element}";
    }

    @Override
    public void execute(String[] args) {
        RouteDistanceComparator comparator = new RouteDistanceComparator();

        CollectionHandler<HashSet<Route>, Route> collectionHandler = RoutesHandler.getInstance();

        Route greaterThan = handler.buildObject();
        System.out.println("Distance: " + greaterThan.getDistance());
        var iterator = collectionHandler.getCollection().iterator();

        int count = 0;

        while (iterator.hasNext()) {
            var current = iterator.next();
            System.out.print("Comparing: current -- " + current.getDistance() + " vs " + greaterThan.getDistance());
            if (comparator.compare(current, greaterThan) > 0) {
                System.out.println(" -- Greater / Removing...");
                System.out.println("Removing element: " + current);
                iterator.remove();
                count++;
            } else {
                System.out.println(" -- Lower.");
            }
        }

        System.out.println("Removed " + count + " elements");
    }

    @Override
    public CommandResponse getResponse() {
        return response;
    }
}