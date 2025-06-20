package com.axonivy.ivy.engine.cli.profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ProfileManager {

  public static void write(Profile profile) {
    var properties = new Properties();
    properties.put("url", profile.url());
    properties.put("user", profile.user());
    properties.put("password", profile.password());
    try (var out = Files.newOutputStream(path())) {
      properties.store(out, "");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static Profile read() {
    if (!Files.exists(path())) {
      return new Profile("http://localhost:8080", "admin", "admin");
    }
    var properties = new Properties();
    try (var in = Files.newInputStream(path())) {
      properties.load(in);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    var url = (String) properties.get("url");
    var user = (String) properties.get("user");
    var password = (String) properties.get("password");
    return new Profile(url, user, password);
  }

  private static Path path() {
    return Path.of(System.getProperty("user.home")).resolve(".ivy-engine-cli");
  }
}
