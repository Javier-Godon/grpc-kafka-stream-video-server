package es.bluesolution.grpckafka_stream_video_server;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TracingConfiguration {

//  @Bean
//  public ObservationHandler<Context> observationTextPublisher() {
//    return new ObservationTextPublisher(log::info);
//  }

  @Bean
  public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }

//  @Bean
//  SpanExporter otlpHttpSpanExporter() {
//    return OtlpHttpSpanExporter
//        .builder()
//        .addHeader("Content-Type", "application/x-protobuf")
//        .setEndpoint("http://localhost:4317")
//        .build();
//  }

}
