package dev.mqzen.commands.help;

import dev.mqzen.commands.base.syntax.CommandSyntax;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public interface HelpMessageProvider {


	@NotNull Style lineStyle();

	@NotNull TextComponent header();

	@NotNull <S> Style syntaxStyle(@NotNull CommandSyntax<S> syntax);

}
