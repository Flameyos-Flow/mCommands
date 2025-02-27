package io.github.mqzn.commands.arguments;

import io.github.mqzn.commands.base.Command;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class ArgumentWord extends AbstractArgument<String> {

	ArgumentWord(String id) {
		super(id, String.class);
	}

	ArgumentWord(ArgumentData data) {
		super(data, String.class);
	}

	@Override
	public String parse(@NotNull Command<?> command, @NotNull String input) {
		return input;
	}

	@Override
	public @NotNull List<String> suggestions() {
		return Collections.emptyList();
	}
}
