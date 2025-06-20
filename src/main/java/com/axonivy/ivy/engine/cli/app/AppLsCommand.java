package com.axonivy.ivy.engine.cli.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Callable;

import com.axonivy.ivy.engine.cli.profile.Profile;
import com.axonivy.ivy.engine.cli.profile.ProfileManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import picocli.CommandLine.Command;

@Command(
        name = "ls",
        description = "Prints all applications."
)
public class AppLsCommand implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {
    var profile = ProfileManager.read();
    var apiUrl = URI.create(profile.url()).resolve("system/api/apps");

    try (var client = HttpClient.newHttpClient()) {
      var request = HttpRequest.newBuilder(apiUrl)
              .header("Authorization", auth(profile))
              .build();
      var response = client.send(request, BodyHandlers.ofString());


      var mapper = new ObjectMapper();
      var apps = mapper.readValue(response.body(), new TypeReference<List<App>>() {});

      System.out.println("-----------------------------------");
      printRow("Name", "State");
      System.out.println("-----------------------------------");
      for (var app : apps) {
        printRow(app.name, app.activityState);
      }
      System.out.println("-----------------------------------");
    }
    return 0;
  }

  private String auth(Profile profile) {
    return "Basic " + Base64.getEncoder().encodeToString((profile.user() + ":" + profile.password()).getBytes());
  }

  public static void printRow(String... columns) {
    for (String column : columns) {
        System.out.printf("| %-15s", column);
    }
    System.out.println("|");
}

  public static class App {

    public String name;
    public String activityState;
  }

  public static void main(String[] args) {
    System.out.println(URI.create("http://localhost:8080/").resolve("system/api/apps"));
  }
}
