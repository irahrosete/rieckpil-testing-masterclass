package de.rieckpil.courses.book.management;

import java.io.IOException;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenLibraryApiClientTest {
  private MockWebServer mockWebServer;
  private OpenLibraryApiClient cut;

  @BeforeEach
  public void setup() throws IOException {
    HttpClient httpClient =
        HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1_000)
            .doOnConnected(
                connection ->
                    connection
                        .addHandlerLast(new ReadTimeoutHandler(1))
                        .addHandlerLast(new WriteTimeoutHandler(1)));

    this.mockWebServer = new MockWebServer();
    this.mockWebServer.start();

    this.cut =
        new OpenLibraryApiClient(
            WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(mockWebServer.url("/").toString())
                .build());
  }

  @Test
  void notNull() {
    assertNotNull(cut);
    assertNotNull(mockWebServer);
  }
}
