package es.bluesolution.grpckafka_stream_video_server.framework;

import reactor.core.CorePublisher;

public interface Handler {
  CorePublisher<Object> handle(CommandQuery commandQuery);

}
