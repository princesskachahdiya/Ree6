package de.presti.ree6.commands.impl.community;

import de.presti.ree6.commands.Category;
import de.presti.ree6.commands.CommandEvent;
import de.presti.ree6.commands.interfaces.Command;
import de.presti.ree6.commands.interfaces.ICommand;
import de.presti.ree6.main.Main;
import de.presti.ree6.sql.SQLSession;
import de.presti.ree6.utils.apis.YouTubeAPIHandler;
import de.presti.ree6.utils.data.Data;
import de.presti.wrapper.YouTubeWrapper;
import de.presti.wrapper.entities.channel.ChannelResult;
import de.presti.wrapper.entities.search.ChannelSearchResult;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

/**
 * A Command to activate YouTube Notifications.
 */
@Command(name = "youtubenotifier", description = "command.description.youtubeNotifier", category = Category.COMMUNITY)
public class YouTubeNotifier implements ICommand {

    /**
     * @inheritDoc
     */
    @Override
    public void onPerform(CommandEvent commandEvent) {
        if (!commandEvent.getGuild().getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
            commandEvent.reply(commandEvent.getResource("message.default.needPermission", Permission.MANAGE_WEBHOOKS.getName()));
            return;
        }

        if (!commandEvent.isSlashCommand()) {
            commandEvent.reply(commandEvent.getResource("command.perform.onlySlashSupported"));
            return;
        }

        if (!commandEvent.getMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
            commandEvent.reply(commandEvent.getResource("message.default.insufficientPermission", Permission.MANAGE_WEBHOOKS.getName()));
            return;
        }

        String command = commandEvent.getSubcommand();
        OptionMapping nameMapping = commandEvent.getOption("url");
        OptionMapping channelMapping = commandEvent.getOption("channel");
        OptionMapping messageMapping = commandEvent.getOption("message");

        switch (command) {
            case "list" -> {
                StringBuilder end = new StringBuilder();

                for (String users : SQLSession.getSqlConnector().getSqlWorker().getAllYouTubeChannels(commandEvent.getGuild().getId())) {
                    end.append(users).append("\n");
                }

                commandEvent.reply(commandEvent.getResource("message.youtubeNotifier.list", end.toString()), 10);
            }
            case "add" -> {
                if (nameMapping == null || channelMapping == null) {
                    commandEvent.reply(commandEvent.getResource("message.default.invalidQuery"));
                    return;
                }

                if (!channelMapping.getAsChannel().getGuild().getId().equals(commandEvent.getGuild().getId())) {
                    commandEvent.reply(commandEvent.getResource("message.default.noMention.channel"), 5);
                    return;
                }

                String name = nameMapping.getAsString();

                name = name.replace("https://www.youtube.com/", "")
                        .replace("channel/", "")
                        .replace("user/", "")
                        .replace("c/", "")
                        .replace("@", "");

                ChannelResult channelResult;

                try {
                    channelResult = YouTubeAPIHandler.getInstance().isValidChannelId(name) ?
                            YouTubeAPIHandler.getInstance().getYouTubeChannelById(name) :
                            YouTubeAPIHandler.getInstance().getYouTubeChannelBySearch(name);
                } catch (Exception e) {
                    commandEvent.reply(commandEvent.getResource("message.default.invalidQuery", name), 5);
                    return;
                }

                if (channelResult == null) {
                    commandEvent.reply(commandEvent.getResource("message.default.invalidQuery", name), 5);
                    return;
                }

                name = channelResult.getTitle();

                StandardGuildMessageChannel channel = channelMapping.getAsChannel().asStandardGuildMessageChannel();
                channel.createWebhook(Data.getBotName() + "-YoutubeNotifier-" + name).queue(w -> {
                    if (messageMapping != null) {
                        SQLSession.getSqlConnector().getSqlWorker().addYouTubeWebhook(commandEvent.getGuild().getId(), channel.getIdLong(), w.getId(), w.getToken(), channelResult.getId(), messageMapping.getAsString());
                    } else {
                        SQLSession.getSqlConnector().getSqlWorker().addYouTubeWebhook(commandEvent.getGuild().getId(), channel.getIdLong(), w.getId(), w.getToken(), channelResult.getId());
                    }
                });
                commandEvent.reply(commandEvent.getResource("message.youtubeNotifier.added", name), 5);

                if (!Main.getInstance().getNotifier().isYouTubeRegistered(channelResult.getId())) {
                    Main.getInstance().getNotifier().registerYouTubeChannel(channelResult.getId());
                }
            }
            case "remove" -> {
                if (nameMapping == null) {
                    commandEvent.reply(commandEvent.getResource("message.default.invalidQuery"));
                    return;
                }

                String name = nameMapping.getAsString();
                SQLSession.getSqlConnector().getSqlWorker().removeYouTubeWebhook(commandEvent.getGuild().getId(), name);
                commandEvent.reply(commandEvent.getResource("message.youtubeNotifier.removed", name), 5);

                if (Main.getInstance().getNotifier().isYouTubeRegistered(name)) {
                    Main.getInstance().getNotifier().unregisterYouTubeChannel(name);
                }
            }

            default -> commandEvent.reply(commandEvent.getResource("message.default.invalidOption"));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public CommandData getCommandData() {
        return new CommandDataImpl("youtubenotifier", "command.description.youtubeNotifier")
                .addSubcommands(new SubcommandData("list", "List all YouTube channels."))
                .addSubcommands(new SubcommandData("add", "Add a YouTube Notifier for a specific channel.")
                        .addOption(OptionType.STRING, "url", "The YouTube channel url.", true)
                        .addOption(OptionType.CHANNEL, "channel", "The channel.", true)
                        .addOption(OptionType.STRING, "message", "Custom announcement message.", false))
                .addSubcommands(new SubcommandData("remove", "Remove a YouTube Notifier for a specific channel.")
                        .addOption(OptionType.STRING, "name", "The YouTube channel name of the Notifier.", true));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String[] getAlias() {
        return new String[]{"yt", "ytnotifier"};
    }
}
