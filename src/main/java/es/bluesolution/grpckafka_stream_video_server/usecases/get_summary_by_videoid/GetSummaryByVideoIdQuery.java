package es.bluesolution.grpckafka_stream_video_server.usecases.get_summary_by_videoid;

public final class GetSummaryByVideoIdQuery {

  private final String videoId;

  public GetSummaryByVideoIdQuery(String videoId) {
    this.videoId = videoId;
  }

  public String getVideoId() {
    return videoId;
  }
}
