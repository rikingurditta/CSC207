package com.example.thegame.main.navigationCommands;

import com.example.thegame.main.GAMEID;

import java.util.HashMap;

/** A Command Pattern Director for the NavigationCommands */
public class NavigationDirector {
  /** A local HashMap of the IDs and their respective commands */
  private final HashMap<GAMEID, NavigationCommand> commandMap = new HashMap<>();

  /**
   * Register a new command
   *
   * @param commandName The given command name - the key
   * @param command The given command - the value
   */
  public void register(GAMEID commandName, NavigationCommand command) {
    commandMap.put(commandName, command);
  }

  /**
   * Execute the command with the given command name
   *
   * @param commandName The requested command name
   */
  public void execute(GAMEID commandName) {
    NavigationCommand command = commandMap.get(commandName);
    if (command == null) {
      throw new IllegalStateException("no game registered for " + commandName);
    }
    command.execute();
  }
}
