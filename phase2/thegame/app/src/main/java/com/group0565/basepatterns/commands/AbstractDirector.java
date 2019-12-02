package com.group0565.basepatterns.commands;

import java.util.HashMap;

/**
 * An abstract base class for the Director in the command design pattern
 *
 * @param <T> The command type
 */
public abstract class AbstractDirector<T extends ICommand> {

  /** A local HashMap of the IDs and their respective commands */
  private final HashMap<Enum, T> commandMap = new HashMap<>();

  /**
   * Register a new command
   *
   * @param commandName The given command name - the key
   * @param command The given command - the value
   */
  public void register(Enum commandName, T command) {
    commandMap.put(commandName, command);
  }

  /**
   * Execute the command with the given command name
   *
   * @param commandName The requested command name
   */
  public void execute(Enum commandName) {
    T command = commandMap.get(commandName);
    if (command == null) {
      throw new IllegalStateException("no game registered for " + commandName);
    }
    command.execute();
  }
}
