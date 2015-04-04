package se.fredsfursten.eithonlibraryplugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import se.fredsfursten.plugintools.PluginConfig;

public final class EithonLibraryPlugin extends JavaPlugin implements Listener {
	private static PluginConfig configuration;

	@Override
	public void onEnable() {
		if (configuration == null) {
			configuration = new PluginConfig(this, "config.yml");
		} else {
			configuration.load();
		}
	}

	@Override
	public void onDisable() {
	}
}
