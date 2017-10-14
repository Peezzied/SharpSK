package me.sharpjaws.sharpSK.hooks.mcMMO;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffmcMMOUnlockParty extends Effect {
	private Expression<String> p;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		p = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[mcmmo] unlock party %string%";
	}

	@Override
	protected void execute(Event e) {
		com.gmail.nossr50.party.PartyManager.getParty(p.getSingle(e)).setLocked(false);
	}
}
