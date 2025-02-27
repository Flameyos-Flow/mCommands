package io.github.mqzn.commands.base;

import io.github.mqzn.commands.arguments.Argument;
import io.github.mqzn.commands.arguments.ArgumentInteger;
import io.github.mqzn.commands.base.context.Context;
import io.github.mqzn.commands.base.cooldown.CommandCooldown;
import io.github.mqzn.commands.base.manager.CommandManager;
import io.github.mqzn.commands.base.syntax.CommandExecution;
import io.github.mqzn.commands.base.syntax.CommandSyntax;
import io.github.mqzn.commands.base.syntax.CommandSyntaxBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public sealed interface Command<S> permits Command.Builder.ImmutableCommandImpl {

	/**
	 * Creating a builder instance for
	 * proper building of the command instance
	 *
	 * @param manager the command manager for instantiating the command
	 *                since every command is linked to it's manager
	 * @param name    the command name to start off by using it
	 * @param <P>     the bootstrap
	 * @param <S>     the sender
	 * @return the builder instance created
	 * @see Command
	 */
	static <P, S> Builder<S> builder(CommandManager<P, S> manager, String name) {
		return new Builder<>(manager, name);
	}

	/**
	 * Command Manager instance
	 * that will be used in creation of this command
	 *
	 * @return the manager shared instance
	 */
	CommandManager<?, S> manager();

	/**
	 * The name of the command
	 *
	 * @return the command name
	 */
	String name();

	/**
	 * The info of the command,
	 * like description, permission, and it's aliases
	 *
	 * @return the information of the command
	 */
	@NotNull CommandInfo info();

	/**
	 * Represents a command cooldown
	 *
	 * @return the cooldown of the command
	 */
	@NotNull CommandCooldown cooldown();

	/**
	 * The requirements for the command to be executed
	 *
	 * @return The requirements for the command to be executed
	 */
	@NotNull Set<CommandRequirement<S>> requirements();

	/**
	 * The default command execution when no args are provided !
	 *
	 * @param sender the command executor !
	 */
	void defaultExecution(S sender, Context<S> commandContext);

	/**
	 * The syntaxes registered for this command
	 *
	 * @return The syntaxes registered for this command
	 * @see CommandSyntax
	 */
	@NotNull List<CommandSyntax<S>> syntaxes();


	default boolean hasCooldown() {
		return !cooldown().isEmpty();
	}

	/**
	 * An internal builder class for the command
	 * class {@link Command<S>}
	 *
	 * @param <S> the sender type
	 * @see Command
	 * @see CommandManager
	 * @see CommandSyntax
	 * @see CommandRequirement
	 */
	final class Builder<S> {

		@NotNull
		private final CommandManager<?, S> manager;

		@NotNull
		private final String name;
		@NotNull
		private final Set<CommandRequirement<S>> requirements = new HashSet<>();
		@NotNull
		private final List<CommandSyntax<S>> syntaxes = new ArrayList<>();
		@NotNull
		private CommandCooldown cooldown = CommandCooldown.EMPTY;
		@NotNull
		private CommandInfo info = CommandInfo.EMPTY_INFO;

		private CommandExecution<S, S> defaultExecutor;

		Builder(@NotNull CommandManager<?, S> manager, @NotNull String name) {
			this.manager = manager;
			this.name = name;
		}

		public Builder<S> info(@NotNull CommandInfo info) {
			this.info = info;
			return this;
		}


		public Builder<S> requirement(@NotNull CommandRequirement<S> requirement) {
			this.requirements.add(requirement);
			return this;
		}

		public Builder<S> syntax(@NotNull CommandSyntax<S> syntax) {
			this.syntaxes.add(syntax);
			return this;
		}


		public Builder<S> defaultExecutor(@NotNull CommandExecution<S, S> execution) {
			this.defaultExecutor = execution;
			return this;
		}

		public Builder<S> cooldown(@NotNull CommandCooldown cooldown) {
			this.cooldown = cooldown;
			return this;
		}


		public synchronized Command<S> build() {
			if (manager.helpProvider() != null) {

				ArgumentInteger pageArg = (ArgumentInteger) Argument.integer("page").min(1);
				pageArg.setOptional(true);
				pageArg.setDefaultValue(1);

				CommandSyntax<S> helpSyntax =
								CommandSyntaxBuilder.<S, S>genericBuilder(manager.getSenderWrapper().senderType(), name).execute((sender, context) -> {

													@Nullable Integer page = context.getArgument("page");
													if (page == null) return;
													manager.handleHelpProvider(sender, context, name, page, syntaxes);
												}
								).argument(Argument.literal("help")).argument(pageArg).build();

				syntaxes.add(helpSyntax);
			}

			return new ImmutableCommandImpl<>(manager, name, info, cooldown, requirements, syntaxes, defaultExecutor);
		}


		record ImmutableCommandImpl<S>(@NotNull CommandManager<?, S> manager,
		                               @NotNull String name,
		                               @NotNull CommandInfo info,
		                               @NotNull CommandCooldown cooldown,
		                               @NotNull Set<CommandRequirement<S>> requirements,
		                               @NotNull List<CommandSyntax<S>> syntaxes,
		                               @Nullable CommandExecution<S, S> execution) implements Command<S> {

			/**
			 * The name of the command
			 *
			 * @return the command name
			 */
			@Override
			public String name() {
				return name;
			}

			/**
			 * The info of the command,
			 * like description, permission, and it's aliases
			 *
			 * @return the information of the command
			 */
			@Override
			public @NotNull CommandInfo info() {
				return info;
			}

			/**
			 * The requirements for the command to be executed
			 *
			 * @return The requirements for the command to be executed
			 */
			@Override
			public @NotNull Set<CommandRequirement<S>> requirements() {
				return requirements;
			}

			/**
			 * The default command execution when no args are provided !
			 *
			 * @param sender the command executor !
			 */
			@Override
			public void defaultExecution(@NotNull S sender, @NotNull Context<S> commandContext) {
				if (execution != null)
					execution.execute(sender, commandContext);
			}


		}


	}

}
