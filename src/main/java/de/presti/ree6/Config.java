package de.presti.ree6;

import org.simpleyaml.configuration.MemorySection;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.io.IOException;

/**
 * Config.
 */
public class Config {

    /**
     * The Configuration.
     */
    private YamlFile yamlFile;

    /**
     * Initialize the Configuration.
     */
    public void init() {

        yamlFile = createConfiguration();

        try {
            Path storage = Path.of("storage");
            Path storageTemp = Path.of("storage/tmp");

            if (!Files.exists(storage))
                Files.createDirectory(storage);

            if (!Files.exists(storageTemp))
                Files.createDirectory(storageTemp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (!getFile().exists()) {
            yamlFile.options().copyHeader();
            yamlFile.options().copyDefaults();
            yamlFile.options().header("""
                    ################################
                    #                              #
                    # Ree6 Config File             #
                    # by Presti                    #
                    #                              #
                    ################################
                    """);
            yamlFile.path("config")
                    .comment("Do not change this!")
                    .path("version").addDefault("3.0.8")
                    .parent().path("creation").addDefault(System.currentTimeMillis());

            yamlFile.path("hikari")
                    .comment("HikariCP Configuration").blankLine()
                    .path("sql").comment("SQL Configuration").blankLine()
                    .path("user").addDefault("root")
                    .parent().path("db").addDefault("root")
                    .parent().path("pw").addDefault("yourpw")
                    .parent().path("host").addDefault("localhost")
                    .parent().path("port").addDefault(3306)
                    .parent().parent().path("misc").comment("Misc Configuration").blankLine()
                    .path("storage").addDefault("sqlite").commentSide("Possible entries: sqlite, mariadb, postgresql, h2, h2-server")
                    .parent().path("storageFile").addDefault("storage/Ree6.db")
                    .parent().path("createEmbeddedServer").addDefault(false).commentSide("Should an instance of an embedded Server be created? Only used for H2-Server.")
                    .parent().path("poolSize").addDefault(10);

            yamlFile.path("bot")
                    .comment("Discord Application and overall Bot Configuration, used for OAuth, Bot Authentication and customization.").blankLine()
                    .path("tokens").path("release").addDefault("ReleaseTokenhere").commentSide("Token used when set to release build.")
                    .parent().path("beta").addDefault("BetaTokenhere").commentSide("Token used when set to beta build.")
                    .parent().path("dev").addDefault("DevTokenhere").commentSide("Token used when set to dev build.")
                    .parent().parent().path("misc").comment("Configuration for the Bot itself.").blankLine()
                    .path("status").addDefault("ree6.de | %guilds% Servers. (%shard%)").commentSide("The Status of the Bot.")
                    .parent().path("feedbackChannelId").addDefault(0L).commentSide("The Channel used for Feedback.")
                    .parent().path("ownerId").addDefault(321580743488831490L).commentSide("The ID of the Bot Owner. Change this to yours!")
                    .parent().path("predefineInformation").addDefault("""
                            You are Ree6 a Discord bot.
                            """).commentSide("Predefined Information for the AI.")
                    .parent().path("invite").addDefault("https://invite.ree6.de").commentSide("The Invite Link of the Bot.")
                    .parent().path("support").addDefault("https://support.ree6.de").commentSide("The Support Server Link of the Bot.")
                    .parent().path("github").addDefault("https://github.ree6.de").commentSide("The GitHub Link of the Bot.")
                    .parent().path("website").addDefault("https://ree6.de").commentSide("The Website Link of the Bot.")
                    .parent().path("webinterface").addDefault("https://cp.ree6.de").commentSide("The Webinterface Link of the Bot.")
                    .parent().path("recording").addDefault("https://cp.ree6.de/external/recording").commentSide("The Recording Link of the Bot.")
                    .parent().path("twitchAuth").addDefault("https://cp.ree6.de/external/twitch").commentSide("The Twitch Authentication Link of the Bot.")
                    .parent().path("advertisement").addDefault("powered by Tube-hosting").commentSide("The Advertisement in Embed Footers and the rest.")
                    .parent().path("name").addDefault("Ree6").commentSide("The Name of the Bot.")
                    .parent().path("shards").addDefault(1).commentSide("The shard amount of the Bot. Check out https://anidiots.guide/understanding/sharding/#sharding for more information.")
                    .parent().path("hideModuleNotification").addDefault(false).commentSide("Should the Notification for disabled Modules be hidden?")
                    .parent().path("debug").addDefault(false).commentSide("Should the Bot be in Debug Mode? This will enable more logging.")
                    .parent().path("modules").comment("Customize the active modules in Ree6.").blankLine()
                    .path("moderation").addDefault(true).commentSide("Enable the moderation module.")
                    .parent().path("music").addDefault(true).commentSide("Enable the music module.")
                    .parent().path("fun").addDefault(true).commentSide("Enable the fun commands.")
                    .parent().path("community").addDefault(true).commentSide("Enable the community commands.")
                    .parent().path("economy").addDefault(true).commentSide("Enable the economy commands.")
                    .parent().path("level").addDefault(true).commentSide("Enable the level module.")
                    .parent().path("nsfw").addDefault(true).commentSide("Enable the nsfw module.")
                    .parent().path("info").addDefault(true).commentSide("Enable the info commands.")
                    .parent().path("hidden").addDefault(true).commentSide("Enable the hidden commands.")
                    .parent().path("logging").addDefault(true).commentSide("Enable the logging module.")
                    .parent().path("notifier").addDefault(true).commentSide("Enable the notifier module.")
                    .parent().path("streamtools").addDefault(true).commentSide("Enable the Stream-tools module.")
                    .parent().path("temporalvoice").addDefault(true).commentSide("Enable the Temporal-voice module.")
                    .parent().path("tickets").addDefault(true).commentSide("Enable the Tickets module.")
                    .parent().path("suggestions").addDefault(true).commentSide("Enable the suggestions module.")
                    .parent().path("customcommands").addDefault(true).commentSide("Enable the custom Commands module.")
                    .parent().path("customevents").addDefault(true).commentSide("Enable the custom Events module.")
                    .parent().path("ai").addDefault(true).commentSide("Enable the AI module.")
                    .parent().path("addons").addDefault(false).commentSide("Enable the Addons module.")
                    .parent().path("news").addDefault(true).commentSide("Enable the news command/module.")
                    .parent().path("games").addDefault(true).commentSide("Enable the Games module.")
                    .parent().path("reactionroles").addDefault(true).commentSide("Enable the reaction-roles module.")
                    .parent().path("slashcommands").addDefault(true).commentSide("Enable the slash-commands support.")
                    .parent().path("messagecommands").addDefault(true).commentSide("Enable the message-commands support.");

            yamlFile.path("heartbeat")
                    .comment("Heartbeat Configuration, for status reporting").blankLine()
                    .path("url").addDefault("none").commentSide("The URL to the Heartbeat-Server")
                    .parent().path("interval").addDefault(60);

            yamlFile.path("dagpi").path("apitoken").commentSide("Your Dagpi.xyz API-Token, for tweet image generation!")
                    .addDefault("DAGPI.xyz API-Token");

            yamlFile.setBlankLine("dagpi");

            yamlFile.path("amari").path("apitoken").commentSide("Your Amari API-Token, for Amari Level imports!")
                    .addDefault("Amari API-Token");

            yamlFile.setBlankLine("amari");

            yamlFile.path("openai").path("apiToken").commentSide("Your OpenAI API-Token, for ChatGPT!")
                    .addDefault("OpenAI API-Token")
                    .parent().path("apiUrl").addDefault("https://api.openai.com/v1/chat/completions").commentSide("The URL to the OpenAI API.")
                    .parent().path("model").addDefault("gpt-3.5-turbo-0301").commentSide("The Model used for the OpenAI API.");

            yamlFile.setBlankLine("openai");

            yamlFile.path("sentry").path("dsn").commentSide("Your Sentry DSN, for error reporting!")
                    .addDefault("yourSentryDSNHere");

            yamlFile.setBlankLine("sentry");

            yamlFile.path("spotify")
                    .comment("Spotify Application Configuration, used to parse Spotify Tracks/Playlists to YouTube search queries.").blankLine()
                    .path("client").path("id").addDefault("yourspotifyclientid")
                    .parent().path("secret").addDefault("yourspotifyclientsecret");

            yamlFile.path("twitch")
                    .comment("Twitch Application Configuration, used for the StreamTools and Twitch Notifications.").blankLine()
                    .path("client").path("id").addDefault("yourtwitchclientidhere")
                    .parent().path("secret").addDefault("yourtwitchclientsecrethere");

            yamlFile.path("twitter")
                    .comment("Twitter Application Configuration, used for the Twitter Notifications.").blankLine()
                    .path("bearer").addDefault("yourTwitterBearerToken");

            yamlFile.path("reddit")
                    .comment("Reddit Application Configuration, used for the Reddit Notification.").blankLine()
                    .path("client").path("id").addDefault("yourredditclientid")
                    .parent().path("secret").addDefault("yourredditclientsecret");

            yamlFile.path("instagram")
                    .comment("Instagram Application Configuration, used for the Instagram Notification.").blankLine()
                    .path("username").addDefault("yourInstagramUsername")
                    .parent().path("password").addDefault("yourInstagramPassword");

            try {
                yamlFile.save(getFile());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                yamlFile.load();
                migrateOldConfig();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Migrate configs to newer versions
     */
    public void migrateOldConfig() {
        String configVersion = getConfigVersion();

        if (shouldSkipMigration(configVersion)) {
            return;
        }

        backupOldConfig();

        processMigration(configVersion);
    }

    private String getConfigVersion() {
        return yamlFile.getString("config.version", "1.9.0");
    }

    private boolean shouldSkipMigration(String configVersion) {
        return compareVersion(configVersion, "3.0.8") || configVersion.equals("3.0.8");
    }

    private void backupOldConfig() {
        try {
            Files.copy(getFile().toPath(), new File("config-old.yml").toPath());
        } catch (IOException ignore) {
            handleBackupFailure();
        }
    }

    private void handleBackupFailure() {
        System.out.println("Could not move the old configuration file to config-old.yml!");
        System.out.println("This means the config file is not backed up by us!");
    }

    private void processMigration(String configVersion) {
        Map<String, Object> resources = yamlFile.getValues(true);

        if (getFile().delete()) {
            init();

            for (Map.Entry<String, Object> entry : resources.entrySet()) {
                migrateEntry(configVersion, entry);
            }

            saveYamlFile();
        }
    }

    private void migrateEntry(String configVersion, Map.Entry<String, Object> entry) {
        String key = entry.getKey();

        if (shouldSkipMigrationEntry(configVersion, key, entry.getValue())) {
            return;
        }

        boolean modified = migrateToVersion(configVersion, key, entry);

        if (!modified) {
            yamlFile.set(key, entry.getValue());
        }
    }

    private boolean shouldSkipMigrationEntry(String configVersion, String key, Object value) {
        return key.startsWith("config") || value instanceof MemorySection || shouldSkipSpecificMigration(configVersion, key);
    }

    private boolean shouldSkipSpecificMigration(String configVersion, String key) {
        return (compareVersion("2.4.11", configVersion) && (key.startsWith("twitter") && !key.endsWith("bearer")))
                || (compareVersion("2.2.0", configVersion) && key.startsWith("youtube"));
    }

    private boolean migrateToVersion(String configVersion, String key, Map.Entry<String, Object> entry) {
        boolean modified = false;

        if (compareVersion("1.10.0", configVersion)) {
            modified = migrateToVersion110(key, entry);
        }

        if (compareVersion("2.2.0", configVersion)) {
            modified = migrateToVersion220(key, entry) || modified;
        }

        return modified;
    }

    private boolean migrateToVersion110(String key, Map.Entry<String, Object> entry) {
        if (key.startsWith("mysql")) {
            key = key.replace("mysql", "hikari.sql");
        }

        if (key.endsWith(".rel")) {
            key = key.replace(".rel", ".release");
        }

        yamlFile.set(key, entry.getValue());
        return true;
    }

    private boolean migrateToVersion220(String key, Map.Entry<String, Object> entry) {
        if (!key.startsWith("youtube")) {
            yamlFile.set(key, entry.getValue());
            return true;
        }
        return false;
    }

    private void saveYamlFile() {
        try {
            yamlFile.save(getFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Compare two version that are based on the x.y.z format.
     *
     * @param versionA the base version.
     * @param versionB the version that should be tested against versionA.
     * @return True if versionA is above versionB.
     */
    public boolean compareVersion(String versionA, String versionB) {
        if (versionA == null) return false;
        if (versionB == null) return true;

        String[] split = versionA.split("\\.");

        int mayor = Integer.parseInt(split[0]);
        int minor = Integer.parseInt(split[1]);
        int patch = Integer.parseInt(split[2]);

        String[] split2 = versionB.split("\\.");
        int otherMayor = Integer.parseInt(split2[0]);
        int otherMinor = Integer.parseInt(split2[1]);
        int otherPatch = Integer.parseInt(split2[2]);

        if (mayor > otherMayor) return true;
        if (mayor == otherMayor && minor > otherMinor) return true;
        return mayor == otherMayor && minor == otherMinor && patch > otherPatch;
    }

    /**
     * Create a new Configuration.
     *
     * @return The Configuration as {@link YamlFile}.
     */
    public YamlFile createConfiguration() {
        try {
            return new YamlFile(getFile());
        } catch (Exception e) {
            return new YamlFile();
        }
    }

    /**
     * Get the Configuration.
     *
     * @return The Configuration as {@link YamlFile}.
     */
    public YamlFile getConfiguration() {
        if (yamlFile == null) {
            init();
        }

        return yamlFile;
    }

    /**
     * Get the Configuration File.
     *
     * @return The Configuration File as {@link File}.
     */
    public File getFile() {
        return new File("config.yml");
    }

}
