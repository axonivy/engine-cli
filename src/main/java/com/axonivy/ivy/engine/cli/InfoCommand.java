package com.axonivy.ivy.engine.cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.concurrent.Callable;

import com.axonivy.ivy.engine.cli.profile.Profile;
import com.axonivy.ivy.engine.cli.profile.ProfileManager;

import picocli.CommandLine.Command;

@Command(
        name = "info",
        description = "Info about the connected Axon Ivy Engine"
)
public class InfoCommand implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {
    var profile = ProfileManager.read();
    var apiUrl = URI.create(profile.url()).resolve("system/api/info?pretty");

    try (var client = HttpClient.newHttpClient()) {
      var request = HttpRequest.newBuilder(apiUrl)
              .header("Authorization", auth(profile))
              .build();
      var response = client.send(request, BodyHandlers.ofString());
      System.out.println(response.body());
    }
    return 0;
  }

  private String auth(Profile profile) {
    return "Basic " + Base64.getEncoder().encodeToString((profile.user() + ":" + profile.password()).getBytes());
  }
}
