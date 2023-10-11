package es.bluesolution.grpckafka_stream_video_server.usecases.get_summary_by_videoid.rest;

import es.bluesolution.grpckafka_stream_video_server.persistence.VideoDocument;
import es.bluesolution.grpckafka_stream_video_server.persistence.VideoRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import java.util.Map;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("grpc-kafka")
public class GetSummaryByVideoIdController {

  private final ObservationRegistry registry;
  private final Tracer tracer;
  private final VideoRepository videoRepository;

  public GetSummaryByVideoIdController(ObservationRegistry registry, Tracer tracer,
      VideoRepository videoRepository) {
    this.registry = registry;
    this.tracer = tracer;
    this.videoRepository = videoRepository;
  }

  @GetMapping(value = "/summary", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Observed(name = "getChunkByVideoId")
  @Timed("getChunkByVideoId.time")
  public Mono<GetSummaryByVideoIdResponse> getChunkByVideoId(
      @RequestBody GetSummaryByVideoIdRequest request) {

    Flux<VideoDocument> fluxOfChunks = videoRepository.findAllByVideoIdOrderByChunkIndexAsc(
        request.getVideoId());

    Mono<Map<Integer, Integer>> mapBytesByChunk = fluxOfChunks.collectMap(
        VideoDocument::getChunkIndex,
        videoDocument -> videoDocument.getData().length
    );

    Span echoSpan = tracer.nextSpan().name("summary").start();
    echoSpan.end();

    return Mono.zip(
            fluxOfChunks.count(),
            mapBytesByChunk,
            (aLong, integerMap) -> new GetSummaryByVideoIdResponse(request.getVideoId(),
                aLong.intValue(), integerMap))
        .name("getChunkByVideoId")
        .tag("traceId", UUID.randomUUID().toString())
        .tap(Micrometer.observation(registry));
  }

}
