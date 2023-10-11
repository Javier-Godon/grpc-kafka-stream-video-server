package es.bluesolution.grpckafka_stream_video_server;

import lombok.Builder;

@Builder
public class LogMessage {

  private String step;
  private String message;
  private String stringPayload;
  private String errorType;

  @Override
  public String toString() {
    return "LogMessage{" +
        "step='" + step + '\'' +
        ", message='" + message + '\'' +
        ", stringPayload='" + stringPayload + '\'' +
        ", errorType='" + errorType + '\'' +
        '}';
  }
}
