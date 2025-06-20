package com.axonivy.ivy.engine.cli;

import picocli.CommandLine.Command;

@Command(name = "engine-cli", mixinStandardHelpOptions = true, description = "CLI for the Axon Ivy Engine", subcommands = {ConnectCommand.class, InfoCommand.class, ApplicationCommand.class, VariablesCommand.class})
public class MainCommand  {


}
