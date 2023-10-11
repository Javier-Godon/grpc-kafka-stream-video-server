package es.bluesolution.grpckafka_stream_video_server;

import java.io.IOException;
import java.nio.channels.Channels;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RequestResponseLoggingFilter implements WebFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest httpRequest = exchange.getRequest();
    final String httpUrl = httpRequest.getURI().toString();

    ServerHttpRequestDecorator loggingServerHttpRequestDecorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
      String requestBody = "";

      @Override
      public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(dataBuffer -> {
          try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Channels.newChannel(byteArrayOutputStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
            requestBody = IOUtils.toString(byteArrayOutputStream.toByteArray(), "UTF-8");
            LOGGER.info(LogMessage.builder()
                .step(httpUrl)
                .message("log incoming http request")
                .stringPayload(requestBody)
                .build().toString());
          } catch (IOException e) {
            LOGGER.error(LogMessage.builder()
                .step("log incoming request for " + httpUrl)
                .message("fail to log incoming http request")
                .errorType("IO exception")
                .stringPayload(requestBody)
                .build().toString(), e);
          }
        });
      }
    };

    ServerHttpResponseDecorator loggingServerHttpResponseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
      String responseBody = "";
      @Override
      public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        Mono<DataBuffer> buffer = Mono.from(body);
        return super.writeWith(buffer.doOnNext(dataBuffer -> {
          try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Channels.newChannel(byteArrayOutputStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
            responseBody = IOUtils.toString(byteArrayOutputStream.toByteArray(), "UTF-8");
            LOGGER.info(LogMessage.builder()
                .step("log outgoing response for " + httpUrl)
                .message("incoming http request")
                .stringPayload(responseBody)
                .build().toString());
          } catch (Exception e) {
            LOGGER.error(LogMessage.builder()
                .step("log outgoing response for " + httpUrl)
                .message("fail to log http response")
                .errorType("IO exception")
                .stringPayload(responseBody)
                .build().toString(), e);
          }
        }));
      }
    };
    return chain.filter(exchange.mutate().request(loggingServerHttpRequestDecorator).response(loggingServerHttpResponseDecorator).build());
  }
}
