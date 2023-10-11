package es.bluesolution.grpckafka_stream_video_server;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingWebFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    serverWebExchange.getResponse()
        .getHeaders().add("web-filter", "web-filter-test");
    return webFilterChain.filter(serverWebExchange);
  }
}
