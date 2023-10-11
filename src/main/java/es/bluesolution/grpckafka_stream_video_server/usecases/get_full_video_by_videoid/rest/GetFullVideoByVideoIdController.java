package es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.rest;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQueryMediator;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.GetFullVideoByVideoIdPayload;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.GetFullVideoByVideoIdQuery;
import es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.GetFullVideoByVideoIdResult;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Log4j2
@RestController
@RequestMapping("grpc-kafka")
public class GetFullVideoByVideoIdController {

  private final CommandQueryMediator mediator;

  public GetFullVideoByVideoIdController(
      @Qualifier("registerFullVideoByVideoIdMediator") CommandQueryMediator mediator) {
    this.mediator = mediator;
  }

  @GetMapping(value = "/full", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<GetFullVideoByVideoIdResponse> getFullVideoByVideoId(
      @RequestBody GetFullVideoByVideoIdRequest request) {
    GetFullVideoByVideoIdQuery query = new GetFullVideoByVideoIdQuery(
        new GetFullVideoByVideoIdPayload(request.getChunkIndex(), request.getVideoId()));
    Publisher<Object> reactorInterfaceOfObjects = mediator.execute(query);
    Flux<GetFullVideoByVideoIdResult> fluxOfVideoResults = Flux.from(reactorInterfaceOfObjects).map(
        GetFullVideoByVideoIdResult.class::cast);
    return fluxOfVideoResults.flatMap(resultToResponse);
  }

  private final Function<GetFullVideoByVideoIdResult, Flux<GetFullVideoByVideoIdResponse>> resultToResponse = result ->
      Flux.just(GetFullVideoByVideoIdResponse.builder()
          .videoId(result.getVideoId())
          .chunkIndex(result.getChunkIndex())
          .bytes(result.getBytes())
          .build());
}
