package me.sharpjaws.sharpSK.hooks.WorldEdit;


import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.sharpjaws.sharpSK.main;

public class EffSaveSelectionToClipboard extends Effect{
	private Expression<Location> point1;
	private Expression<Location> point2;
	private Expression<Player> pl;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		point1 = (Expression<Location>) expr[0];
		point2 = (Expression<Location>) expr[1];
		pl = (Expression<Player>) expr[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "[sharpsk] [worldedit] save [selection] p[oint]1 %location% p[oint]2 %location% to clip[board] of [player] %player%";
	}

	@Override
	protected void execute(Event e) {
		WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		LocalSession session = wep.getSession(pl.getSingle(e));

		Location point1loc = point1.getSingle(e);
		Location point2loc = point2.getSingle(e);

		Vector min = new Vector(point1loc.getBlockX(),point1loc.getBlockY(),point1loc.getZ());
		Vector max = new Vector(point2loc.getBlockX(),point2loc.getBlockY(),point2loc.getZ());
		CuboidRegion cr =  new CuboidRegion(min, max);
		BlockArrayClipboard bc = new BlockArrayClipboard(cr);
		EditSession es = wep.createEditSession(pl.getSingle(e));
		try {
			bc.setOrigin(session.getPlacementPosition(wep.wrapPlayer(pl.getSingle(e))));
			session.setClipboard(new ClipboardHolder(bc,es.getWorld().getWorldData()));
			ForwardExtentCopy copy = new ForwardExtentCopy(es,cr, bc, cr.getMinimumPoint());
			Operations.complete(copy);

		} catch (WorldEditException e1) {
			main core = (main)Bukkit.getPluginManager().getPlugin("SharpSK");
			core.getLogger().warning("Failed to save selection. Something went wrong");
			return;
		}



	}
}
