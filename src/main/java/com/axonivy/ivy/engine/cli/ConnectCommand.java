package com.axonivy.ivy.engine.cli;

import java.util.concurrent.Callable;

import com.axonivy.ivy.engine.cli.profile.Profile;
import com.axonivy.ivy.engine.cli.profile.ProfileManager;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
        name = "connect",
        description = "Connect to an Axon Ivy Engine"
)
public class ConnectCommand implements Callable<Integer> {

  @Parameters(index = "0", description = "URI")
  private String uri;

  @Parameters(index = "1", description = "Username")
  private String user;

  @Parameters(index = "2", description = "Password")
  private String password;

  @Override
  public Integer call() throws Exception {
    var profile = new Profile(uri, user, password);
    ProfileManager.write(profile);
    return 0;
  }
}
