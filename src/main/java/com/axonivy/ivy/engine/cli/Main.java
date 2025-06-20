package com.axonivy.ivy.engine.cli;

import picocli.CommandLine;

public class Main {

  public static void main(String[] args) {
    int exitCode = new CommandLine(new MainCommand()).execute(args);
    System.exit(exitCode);
  }
}
