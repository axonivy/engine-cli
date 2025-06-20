package com.axonivy.ivy.engine.cli.app;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "app",
        mixinStandardHelpOptions = true,
        description = "Manage applications",
        subcommands = {AppLsCommand.class}
)
public class AppCommand implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {
    var cmd = new CommandLine(new AppCommand());
    cmd.usage(System.out);
    return 0;
  }
}
