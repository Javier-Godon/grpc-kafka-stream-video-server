package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetChunkByVideoIdResult {

  private int chunkIndex;
  private String videoId;
  private byte[] bytes;

}
