package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid;

public final class GetChunkByVideoIdPayload {

  private final int chunkIndex;
  private final String videoId;

  public GetChunkByVideoIdPayload(int chunkIndex, String videoId) {
    this.chunkIndex = chunkIndex;
    this.videoId = videoId;
  }

  public int getChunkIndex() {
    return chunkIndex;
  }

  public String getVideoId() {
    return videoId;
  }
}
