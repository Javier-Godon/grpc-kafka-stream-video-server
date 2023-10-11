package es.bluesolution.grpckafka_stream_video_server.usecases.get_summary_by_videoid.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetSummaryByVideoIdRequest {

  private String videoId;
}
