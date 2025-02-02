package me.sharpjaws.sharpsk.hooks.AuthmeReloaded;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffAuthForceLogin extends Effect {
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, SkriptParser.ParseResult paramParseResult) {
        player = (Expression<Player>) expr[0];

        return true;
    }

    @Override
    public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
        return "force login %player%";
    }

    @Override
    protected void execute(Event e) {
        AuthMeApi.getInstance().forceLogin(player.getSingle(e));
    }
}
