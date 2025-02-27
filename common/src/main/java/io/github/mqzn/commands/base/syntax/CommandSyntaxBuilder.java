package io.github.mqzn.commands.base.syntax;

import io.github.mqzn.commands.arguments.Argument;
import io.github.mqzn.commands.base.Information;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandSyntaxBuilder<S, C> {

	@NotNull
	private final String commandLabel;
	@NotNull
	private final List<Argument<?>> arguments = new ArrayList<>();
	@Nullable
	private Class<C> senderClass;
	private CommandExecution<S, C> execution;
	@NotNull
	private SyntaxFlags flags = SyntaxFlags.of();

	@Nullable
	private Information info = null;

	protected CommandSyntaxBuilder(@NotNull Class<C> senderClass,
	                               @NotNull String label) {
		this.senderClass = senderClass;
		this.commandLabel = label;
	}

	public static <S, C> CommandSyntaxBuilder<S, C> genericBuilder(@NotNull Class<C> senderClass,
	                                                               @NotNull String label) {
		return new CommandSyntaxBuilder<>(senderClass, label);
	}

	public CommandSyntaxBuilder<S, C> info(@Nullable Information info) {
		this.info = info;
		return this;
	}

	public CommandSyntaxBuilder<S, C> flags(String... flags) {
		this.flags = SyntaxFlags.of(flags);
		return this;
	}

	public CommandSyntaxBuilder<S, C> flags(SyntaxFlags flags) {
		this.flags = flags;
		return this;
	}

	public CommandSyntaxBuilder<S, C> senderType(@Nullable Class<C> senderClass) {
		this.senderClass = senderClass;
		return this;
	}

	public CommandSyntaxBuilder<S, C> argument(@NotNull Argument<?> argument) {
		arguments.add(argument);
		return this;
	}

	public CommandSyntaxBuilder<S, C> execute(@NotNull CommandExecution<S, C> execution) {
		this.execution = execution;
		return this;
	}

	public CommandSyntax<S> build() {
		CommandSyntax<S> syntax = new CommandSyntax<>(senderClass, commandLabel, execution, flags, arguments);
		syntax.setInfo(info);
		return syntax;
	}

}
