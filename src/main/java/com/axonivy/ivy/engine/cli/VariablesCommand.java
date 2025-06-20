package com.axonivy.ivy.engine.cli;

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
import picocli.CommandLine.Parameters;

@Command(
        name = "variables",
        description = "Prints all variables."
)
class VariablesCommand implements Callable<Integer> {

  @Parameters(index = "0", description = "Application")
  private String app;

  @Override
  public Integer call() throws Exception {
    var profile = ProfileManager.read();
    var variablesUrl = URI.create(profile.url()).resolve("system/api/apps/" + app + "/variables");

    try (var client = HttpClient.newHttpClient()) {
      var request = HttpRequest.newBuilder(variablesUrl)
              .header("Authorization", auth(profile))
              .build();
      var response = client.send(request, BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        var mapper = new ObjectMapper();
        var vars = mapper.readValue(response.body(), new TypeReference<List<Variable>>() {});

        for (var v : vars) {
          var val = v.value;
          if (val.length() > 50) {
            val = v.value.substring(0, 50);
          }
          System.out.println(v.name + " " + val);
        }
      } else {
        System.err.println(response.body());
      }
    }
    return 0;
  }

  private String auth(Profile profile) {
    return "Basic " + Base64.getEncoder().encodeToString((profile.user() + ":" + profile.password()).getBytes());
  }

  public static class Variable {

    public String name;
    public String value;
  }
}
