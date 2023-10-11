package es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQuery;
import es.bluesolution.grpckafka_stream_video_server.framework.Handler;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoDocument;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoRepository;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;

public class GetFullVideoByVideoIdHandler implements Handler {

  private final VideoRepository videoRepository;

  public GetFullVideoByVideoIdHandler(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
  }

  @Override
  public CorePublisher<Object> handle(CommandQuery commandQuery) {
    GetFullVideoByVideoIdQuery query = (GetFullVideoByVideoIdQuery)commandQuery;
    GetFullVideoByVideoIdPayload queryPayload = (GetFullVideoByVideoIdPayload)query.getPayload();
    Flux<VideoDocument> videoDocumentFlux = retrieveFluxOfVideos(queryPayload.videoId());
    return videoDocumentFlux.map(this::mapVideoDocumentToResult);
  }

  private Flux<VideoDocument> retrieveFluxOfVideos(String videoId) {
    return videoRepository.findAllByVideoIdOrderByChunkIndexAsc(
        videoId);
  }

  private GetFullVideoByVideoIdResult mapVideoDocumentToResult(VideoDocument videoDocument) {
    GetFullVideoByVideoIdResult response = new GetFullVideoByVideoIdResult();
    response.setVideoId(videoDocument.getVideoId());
    response.setChunkIndex(videoDocument.getChunkIndex());
    response.setBytes(videoDocument.getData());
    return response;
  }
}
