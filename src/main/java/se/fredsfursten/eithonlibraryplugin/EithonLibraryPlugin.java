package se.fredsfursten.eithonlibraryplugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import se.fredsfursten.plugintools.PluginConfig;

public final class EithonLibraryPlugin extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		PluginConfig.enable(this);
	}

	@Override
	public void onDisable() {
	}
}
