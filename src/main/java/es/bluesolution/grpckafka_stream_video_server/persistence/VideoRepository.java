package es.bluesolution.grpckafka_stream_video_server.persistence;

import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoRepository extends ReactiveMongoRepository<VideoDocument, UUID> {

  Flux<VideoDocument> findAllByVideoIdOrderByChunkIndexAsc(String videoId);

  Mono<VideoDocument> findByVideoIdAndChunkIndex(String videoId, Integer chunkIndex);

  Mono<Long> countByVideoId(String videoId);

}
