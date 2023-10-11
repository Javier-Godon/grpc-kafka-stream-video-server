package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQuery;
import es.bluesolution.grpckafka_stream_video_server.framework.Handler;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoDocument;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoRepository;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

public class GetChunkByVideoIdHandler implements Handler {

  private final VideoRepository videoRepository;

  public GetChunkByVideoIdHandler(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  @Override
  public CorePublisher<Object> handle(CommandQuery query) {
//    if (!query.getClass().isInstance(GetChunkByVideoIdQuery.class)) {
//      throw new RuntimeException(String.format("payload is not instance of: %s",
//          GetChunkByVideoIdPayload.class.getName()));
//    }
    GetChunkByVideoIdPayload payload = (GetChunkByVideoIdPayload) query.getPayload();
    Mono<VideoDocument> videoDocumentFound = videoRepository.findByVideoIdAndChunkIndex(
        payload.getVideoId(), payload.getChunkIndex());
    return videoDocumentFound.map(this::fromEntityToResult);

  }

  private GetChunkByVideoIdResult fromEntityToResult (VideoDocument entity){
    GetChunkByVideoIdResult result = new GetChunkByVideoIdResult();
    result.setVideoId(entity.getVideoId());
    result.setChunkIndex(entity.getChunkIndex());
    result.setBytes(entity.getData());
    return result;
  }
}
