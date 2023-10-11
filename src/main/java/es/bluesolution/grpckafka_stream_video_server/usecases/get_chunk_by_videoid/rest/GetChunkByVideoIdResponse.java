package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetChunkByVideoIdResponse {

  private int chunkIndex;
  private String videoId;
  private byte[] bytes;
}
