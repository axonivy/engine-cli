package com.axonivy.ivy.engine.cli;

import com.axonivy.ivy.engine.cli.app.AppCommand;

import picocli.CommandLine.Command;

@Command(name = "engine-cli", mixinStandardHelpOptions = true, description = "CLI for the Axon Ivy Engine", subcommands = {ConnectCommand.class, InfoCommand.class, AppCommand.class, VariablesCommand.class})
public class MainCommand  {


}
