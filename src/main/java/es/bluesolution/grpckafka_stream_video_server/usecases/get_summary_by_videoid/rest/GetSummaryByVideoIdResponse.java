package es.bluesolution.grpckafka_stream_video_server.usecases.get_summary_by_videoid.rest;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetSummaryByVideoIdResponse {

  private String videoId;
  private int numberOfChunks;
  private Map<Integer,Integer> numberOfBytesByChunk;

}
