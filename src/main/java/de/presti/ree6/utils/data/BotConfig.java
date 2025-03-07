package de.presti.ree6.utils.data;

import de.presti.ree6.main.Main;

/**
 * Utility class to access bot-related configurations.
 */
public class BotConfig {

    private static final Main INSTANCE = Main.getInstance();

    private BotConfig() {
        throw new IllegalStateException("Utility class");
    }

    public static String getStatus() {
        return getConfigString("bot.misc.status", "ree6.de | %guilds% Servers. (%shard%)");
    }

    public static String getWebsite() {
        return getConfigString("bot.misc.website", "https://ree6.de");
    }

    public static String getGithub() {
        return getConfigString("bot.misc.github", "https://github.ree6.de");
    }
    /**
     * Get the configured Discord Bot invite.
     * @return the Discord Bot invite from the config.
     */
    public static String getInvite() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.invite", "https://invite.ree6.de");
    }

    /**
     * Get the configured Discord Bot support server.
     * @return the Discord Bot support server from the config.
     */
    public static String getSupport() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.support", "https://support.ree6.de");
    }

    /**
     * Get the configured Discord Bot Feedback channel id.
     * @return the Discord Bot Feedback channel id from the config.
     */
    public static long getFeedbackChannel() {
        return Main.getInstance().getConfig().getConfiguration().getLong("bot.misc.feedbackChannelId", 0);
    }

    /**
     * Get the configured Advertisement.
     * @return the Advertisement from the config.
     */
    public static String getAdvertisement() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.advertisement", "powered by Tube-Hosting");
    }

    /**
     * Get the configured Discord Bot owner.
     * @return the Discord Bot owner from the config.
     */
    public static String getBotOwner() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.ownerId", "321580743488831490");
    }

    /**
     * Get the configured Discord Bot name.
     * @return the Discord Bot name from the config.
     */
    public static String getBotName() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.name", "Ree6");
    }

    /**
     * Check if a module is activated in the config.
     * @param moduleName Name of the module.
     * @return if the module is activated.
     */
    public static boolean isModuleActive(String moduleName) {
        return Main.getInstance().getConfig().getConfiguration().getBoolean("bot.misc.modules." + moduleName, true);
    }

    /**
     * Check if the module notification should be hidden.
     * @return if the module notification should be hidden.
     */
    public static boolean shouldHideModuleNotification() {
        return Main.getInstance().getConfig().getConfiguration().getBoolean("bot.misc.hideModuleNotification", false);
    }

    /**
     * Check if the bot should be able to send the recordings in chat.
     * @return if the bot is allowed to send the recordings in chat.
     */
    public static boolean allowRecordingInChat() {
        return Main.getInstance().getConfig().getConfiguration().getBoolean("bot.misc.allowRecordingInChat", false);
    }

    /**
     * Get the configured default language.
     * @return the default language from the config.
     */
    public static String getDefaultLanguage() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.defaultLanguage", "en-GB");
    }

    /**
     * Get the configured Twitch Auth Url.
     * @return the Twitch Auth Url from the config.
     */
    public static String getTwitchAuth() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.twitchAuth", "https://cp.ree6.de/external/twitch");
    }

    /**
     * Get the configured Recording Url.
     * @return the Recording Url from the config.
     */
    public static String getRecordingUrl() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.recording", "https://cp.ree6.de/external/recording");
    }

    /**
     * Get the configured Webinterface Url.
     * @return the Webinterface Url from the config.
     */
    public static String getWebinterface() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.webinterface", "https://cp.ree6.de");
    }

    /**
     * Get the configured default prefix.
     * @return the default prefix from the config.
     */
    public static String getDefaultPrefix() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.defaultPrefix", "ree!");
    }

    /**
     * Get the configured Font for the text.
     * @return the Font for the text from the config.
     */
    public static String getTextFont() {
        return Main.getInstance().getConfig().getConfiguration().getString("bot.misc.textFont", "Verdana");
    }

    /**
     * Check if the bot should run in debug mode.
     * @return if the bot should run in debug mode.
     */
    public static boolean isDebug() {
        return Main.getInstance().getConfig().getConfiguration().getBoolean("bot.misc.debug", false);
    }

    /**
     * Check if the bot should use LavaLink.
     * @return if the bot should use LavaLink.
     */
    public static boolean shouldUseLavaLink() {
        return Main.getInstance().getConfig().getConfiguration().getBoolean("lavalink.enable", false);
    }

    private static String getConfigString(String path, String defaultValue) {
        return INSTANCE.getConfig().getConfiguration().getString(path, defaultValue);
    }

    private static long getConfigLong(String path, long defaultValue) {
        return INSTANCE.getConfig().getConfiguration().getLong(path, defaultValue);
    }

    private static boolean getConfigBoolean(String path, boolean defaultValue) {
        return INSTANCE.getConfig().getConfiguration().getBoolean(path, defaultValue);
    }
}
