package com.axonivy.ivy.engine.cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import picocli.CommandLine.Command;

@Command(
        name = "apps",
        description = "Prints all applications."
)
class ApplicationCommand implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {
    try (var client = HttpClient.newHttpClient()) {
      var request = HttpRequest.newBuilder(URI.create("http://localhost:8080/system/api/apps"))
              .header("Authorization", auth())
              .build();
      var response = client.send(request, BodyHandlers.ofString());


      var mapper = new ObjectMapper();
      var apps = mapper.readValue(response.body(), new TypeReference<List<App>>() {});

      for (var app : apps) {
        System.out.println(app.name + " " + app.activityState);
      }
    }
    return 0;
  }

  private String auth() {
    return "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes());
  }

  public static class App {

    public String name;
    public String activityState;
  }
}
