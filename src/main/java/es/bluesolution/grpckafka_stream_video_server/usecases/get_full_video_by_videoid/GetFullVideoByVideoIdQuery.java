package es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid;

import es.bluesolution.grpckafka_stream_video_server.framework.CommandQuery;

public final class GetFullVideoByVideoIdQuery implements CommandQuery {

  private final GetFullVideoByVideoIdPayload payload;

  public GetFullVideoByVideoIdQuery(GetFullVideoByVideoIdPayload payload) {
    this.payload = payload;
  }

  @Override
  public Object getPayload() {
    return  this.payload ;
  }
}
