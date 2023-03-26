package commandManager.commands;

import exceptions.BuildObjectException;
import models.Route;
import models.handlers.ModuleHandler;
import models.handlers.userMode.RouteCLIHandler;
import requestLogic.dataTransferObjects.models.RouteDTO;
import requestLogic.dtoMappers.RouteDTOMapper;
import serverLogic.ServerConnectionHandler;
import serverLogic.ServerConnectionUtils;

/**
 * Add element if it's value greater than max value.
 *
 * @author Zerumi
 * @since 1.0
 */
public class AddIfMaxCommand implements BaseCommand {
    private Route route;
    private RouteDTO obj;
    private final ModuleHandler<Route> handler;

    /**
     * Default constructor with handler from 1.0
     */
    public AddIfMaxCommand() {
        handler = new RouteCLIHandler();
    }

    /**
     * Provides choosing handler
     *
     * @since 1.1
     * @param handler ModuleHandler for operating
     */
    public AddIfMaxCommand(ModuleHandler<Route> handler)
    {
        this.handler = handler;
    }

    @Override
    public void execute(String[] args) throws BuildObjectException, ClassNotFoundException {
        route = handler.buildObject();
        obj = RouteDTOMapper.routeDTOMapper(route);
        ServerConnectionUtils.sendCommand(this, args, ServerConnectionHandler.getCurrentConnection());
    }
}
