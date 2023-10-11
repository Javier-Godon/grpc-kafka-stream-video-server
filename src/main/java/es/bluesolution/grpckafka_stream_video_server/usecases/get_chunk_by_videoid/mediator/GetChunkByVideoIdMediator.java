package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.mediator;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQueryMediator;
import es.bluesolution.grpckafka_stream_video_server.framework.Handler;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoRepository;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdHandler;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdQuery;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetChunkByVideoIdMediator {

  private final VideoRepository videoRepository;

  public GetChunkByVideoIdMediator(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  @Bean
  public CommandQueryMediator registerChunkByVideoIdMediator() {
    final Map<String, Handler> dispatcher = new HashMap<>();

    dispatcher.put(GetChunkByVideoIdQuery.class.getSimpleName(),
        new GetChunkByVideoIdHandler(videoRepository));
    return new CommandQueryMediator(dispatcher);
  }

}
