package com.axonivy.ivy.engine.cli;

import picocli.CommandLine.Command;

@Command(name = "engine-cli", description = "CLI for the Axon Ivy Engine", subcommands = {ApplicationCommand.class})
public class MainCommand {


}
