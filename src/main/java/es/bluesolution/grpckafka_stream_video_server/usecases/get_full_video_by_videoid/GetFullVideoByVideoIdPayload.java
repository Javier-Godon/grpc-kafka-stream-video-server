package es.bluesolution.grpckafka_stream_video_server.usecases.get_full_video_by_videoid;

public record GetFullVideoByVideoIdPayload(int chunkIndex, String videoId) {

}