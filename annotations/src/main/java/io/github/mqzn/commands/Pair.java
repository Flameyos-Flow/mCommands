package io.github.mqzn.commands;

import lombok.Data;

@Data(staticConstructor = "of")
public final class Pair<L, R> {


	private final L left;

	private final R right;


}
