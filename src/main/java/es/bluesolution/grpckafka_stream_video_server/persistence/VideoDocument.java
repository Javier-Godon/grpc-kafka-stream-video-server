package es.bluesolution.grpckafka_stream_video_server.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "video")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoDocument {

  private int chunkIndex;
  private String videoId;
  private byte[] data;


}
