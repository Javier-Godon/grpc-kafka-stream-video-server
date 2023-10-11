package es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetFullVideoByVideoIdRequest {

  private int chunkIndex;
  private String videoId;
  private String contentType;
}
