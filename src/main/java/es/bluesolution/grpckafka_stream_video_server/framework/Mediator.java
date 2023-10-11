package es.bluesolution.grpckafka_stream_video_server.framework;

public interface Mediator<T> {
  Object execute(T commandQuery);
}
