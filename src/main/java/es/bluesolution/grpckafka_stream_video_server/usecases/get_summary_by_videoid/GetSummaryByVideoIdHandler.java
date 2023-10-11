package es.bluesolution.grpckafka_stream_video_server.usecases.get_summary_by_videoid;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQuery;
import es.bluesolution.grpckafka_stream_video_server.framework.Handler;
import reactor.core.CorePublisher;

public class GetSummaryByVideoIdHandler implements Handler {

  @Override
  public CorePublisher<Object> handle(CommandQuery commandQuery) {
    return null;
  }
}
