package es.bluesolution.grpckafka_stream_video_server.framework;

import es.bluesolution.grpckafka_stream_video_server.persistence.VideoRepository;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdHandler;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdQuery;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MediatorConfiguration {

  private final VideoRepository videoRepository;

  public MediatorConfiguration(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  @Bean
  public CommandQueryMediator register() {
    final Map<String, Handler> dispatcher = new HashMap<>();

    dispatcher.put(GetChunkByVideoIdQuery.class.getSimpleName(),
        new GetChunkByVideoIdHandler(videoRepository));
    return new CommandQueryMediator(dispatcher);
  }

}
