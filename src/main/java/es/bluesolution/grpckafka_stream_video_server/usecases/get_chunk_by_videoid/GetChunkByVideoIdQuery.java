package es.bluesolution.grpckafka_stream_video_server.usecases.get_chunk_by_videoid;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQuery;

public final class GetChunkByVideoIdQuery implements CommandQuery {

  private final GetChunkByVideoIdPayload payload;

  public GetChunkByVideoIdQuery(GetChunkByVideoIdPayload payload) {
    this.payload = payload;
  }

  @Override
  public GetChunkByVideoIdPayload getPayload() {
    return payload;
  }
}
