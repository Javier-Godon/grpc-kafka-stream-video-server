package es.bluesolution.grpckafka_stream_video_server.framework;

import java.util.Map;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandQueryMediator implements Mediator<CommandQuery> {

  private static final Logger log = LoggerFactory.getLogger(CommandQueryMediator.class);

  private final Map<String, Handler> dispatcher;

  public CommandQueryMediator(Map<String, Handler> dispatcher) {
    this.dispatcher = dispatcher;
  }

  public Publisher<Object> execute(CommandQuery commandQuery) {
    String concreteCommandQuery = commandQuery.getClass().getSimpleName();
    Handler concreteHandler = dispatcher.get(concreteCommandQuery);
    if (log.isInfoEnabled()) {
      log.trace(String.format("executed: %s", concreteCommandQuery));
    }
    return concreteHandler.handle(commandQuery);
  }


}
