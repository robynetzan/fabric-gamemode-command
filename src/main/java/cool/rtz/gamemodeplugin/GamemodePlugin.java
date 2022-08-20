package cool.rtz.gamemodeplugin;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import static net.minecraft.server.command.CommandManager.*;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GamemodePlugin implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("gm")
		.then(argument("gamemode", string())
		.executes(context -> {
			String gamemode = getString(context, "gamemode").toString();
			GameMode gm;

			switch(gamemode) {
				case "s":
				case "survival":
				case "0":
				  gm = GameMode.SURVIVAL;
				  break;
				case "c":
				case "creative":
				case "1":
					gm = GameMode.CREATIVE;
				  break;
				case "a":
				case "adventure":
				case "2":
					gm = GameMode.ADVENTURE;
					break;
				case "spec":
				case "spectator":
				case "3":
					gm = GameMode.SPECTATOR;
					break;
				default:
				  	gm = null;
			}

			if (gm == null) {
				return 0;
			}

			ServerPlayerEntity player = context.getSource().getPlayer();
			player.changeGameMode(gm);
			context.getSource().sendMessage(Text.literal("Changed gamemode to "+gm.getName()));

			return 1;
		}))));
	}
}
