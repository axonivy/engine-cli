package com.axonivy.ivy.engine.cli;

import picocli.CommandLine;

public class Main {

  public static void main(String[] args) {
    var cmd = new CommandLine(new MainCommand());
    if (args.length == 0) {
      cmd.usage(System.out);
      return;
    }
    int exitCode = cmd.execute(args);
    System.exit(exitCode);
  }
}
