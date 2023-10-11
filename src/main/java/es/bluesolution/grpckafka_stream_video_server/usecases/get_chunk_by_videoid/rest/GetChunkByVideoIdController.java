package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.rest;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQueryMediator;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdPayload;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdQuery;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.GetChunkByVideoIdResult;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("grpc-kafka")
public class GetChunkByVideoIdController {

  private final CommandQueryMediator mediator;

  public GetChunkByVideoIdController(
      @Qualifier("registerChunkByVideoIdMediator") CommandQueryMediator mediator) {
    this.mediator = mediator;
  }

  @GetMapping(value = "/chunk", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<GetChunkByVideoIdResponse> getChunkByVideoId(
      @RequestBody GetChunkByVideoIdRequest request) {
    GetChunkByVideoIdPayload payload = new GetChunkByVideoIdPayload(request.getChunkIndex(),
        request.getVideoId());
    GetChunkByVideoIdQuery query = new GetChunkByVideoIdQuery(payload);
    Publisher<Object> result = mediator.execute(query);
    Mono<GetChunkByVideoIdResult> getChunkByVideoIdResultMono = Mono.from(result).map(
        GetChunkByVideoIdResult.class::cast);
    return getChunkByVideoIdResultMono.flatMap(fromResultToResponse);

  }

  private final Function<GetChunkByVideoIdResult, Mono<GetChunkByVideoIdResponse>> fromResultToResponse = result ->
      Mono.just(GetChunkByVideoIdResponse.builder()
          .videoId(result.getVideoId())
          .chunkIndex(result.getChunkIndex())
          .bytes(result.getBytes())
          .build());
}
