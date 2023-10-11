package es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.mediator;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQueryMediator;
import es.bluesolution.grpckafka_stream_video_server.framework.Handler;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoRepository;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.GetFullVideoByVideoIdHandler;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.GetFullVideoByVideoIdQuery;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetFullVideoByVideoIdMediator {

  private final VideoRepository videoRepository;

  public GetFullVideoByVideoIdMediator(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  @Bean
  public CommandQueryMediator registerFullVideoByVideoIdMediator() {
    final Map<String, Handler> dispatcher = new HashMap<>();

    dispatcher.put(GetFullVideoByVideoIdQuery.class.getSimpleName(),
        new GetFullVideoByVideoIdHandler(videoRepository));
    return new CommandQueryMediator(dispatcher);
  }

}
