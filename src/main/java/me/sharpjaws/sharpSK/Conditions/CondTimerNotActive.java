package me.sharpjaws.sharpSK.Conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.sharpjaws.sharpSK.Threads.CTimerThread;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondTimerNotActive extends Condition {
	private Expression<String> timer;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		timer = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "timer %string% is not active";
	}

	@Override
	public boolean check(Event e) {
		boolean active = false;

		for (Thread t : Thread.getAllStackTraces().keySet()) {
			if (t instanceof CTimerThread) {
				if (t.getName().contains(timer.getSingle(e))) {
					active = t.isAlive();

				}
			}
		}
        return active = false;
	}

}
