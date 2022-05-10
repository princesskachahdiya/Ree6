package de.presti.ree6.logger.events.implentation;

import club.minnced.discord.webhook.send.WebhookMessage;
import de.presti.ree6.logger.events.LogMessage;
import de.presti.ree6.logger.events.LogTyp;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

/**
 * This is class is used to store VoiceData for Logs which work
 * with Stuff like the current state of Members in a voice or,
 * the change of their current voice-state.
 */
public class LogMessageVoice extends LogMessage {

    // An instance of the Member Entity.
    private Member member;

    // The Audio Channels associated with the Events.
    private AudioChannel previousVoiceChannel,
            currentVoiceChannel;

    /**
     * Constructor for an Audio Channel join / leave Event.
     *
     * @param webhookId       The ID of the Webhook.
     * @param webhookAuthCode The Auth-Token for the Webhook.
     * @param webhookMessage  WebhookMessage itself.
     * @param guild           The Guild related to the Log-Message
     * @param logTyp          The Typ of the current Log.
     * @param member         the {@link Member} associated with the Event.
     * @param voiceChannel   the {@link AudioChannel} associated with the Event.
     */
    public LogMessageVoice(long webhookId, String webhookAuthCode, WebhookMessage webhookMessage, Guild guild, LogTyp logTyp, Member member, AudioChannel voiceChannel) {
        super(webhookId, webhookAuthCode, webhookMessage, guild, logTyp);
        this.member = member;
        if (logTyp == LogTyp.VC_JOIN) {
            this.currentVoiceChannel = voiceChannel;
        } else if (logTyp == LogTyp.VC_LEAVE) {
            this.previousVoiceChannel = voiceChannel;
        }
    }

    /**
     * Constructor for a Member Audio Channel Move Event.
     *
     * @param webhookId       The ID of the Webhook.
     * @param webhookAuthCode The Auth-Token for the Webhook.
     * @param webhookMessage  WebhookMessage itself.
     * @param guild           The Guild related to the Log-Message
     * @param logTyp          The Typ of the current Log.
     * @param member               the {@link Member} associated with the Event.
     * @param previousVoiceChannel the previous {@link AudioChannel} of the Event.
     * @param currentVoiceChannel  the current {@link AudioChannel} of the Event.
     */
    public LogMessageVoice(long webhookId, String webhookAuthCode, WebhookMessage webhookMessage, Guild guild, LogTyp logTyp, Member member, AudioChannel previousVoiceChannel, AudioChannel currentVoiceChannel) {
        super(webhookId, webhookAuthCode, webhookMessage, guild, logTyp);
        this.member = member;
        this.previousVoiceChannel = previousVoiceChannel;
        this.currentVoiceChannel = currentVoiceChannel;
    }

    /**
     * Get the Member that is associated with the Log.
     *
     * @return the {@link Member}.
     */
    public Member getMember() {
        return member;
    }

    /**
     * Change the associated Member of the Log.
     *
     * @deprecated Will be removed.
     * @param member the new {@link Member}.
     */
    @Deprecated(since = "1.7.7", forRemoval = true)
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Get the previous {@link AudioChannel} of the {@link Member}
     *
     * @return the previous {@link AudioChannel}.
     */
    public AudioChannel getPreviousVoiceChannel() {
        return previousVoiceChannel;
    }

    /**
     * Change the previous {@link AudioChannel} of the {@link Member}
     *
     * @deprecated Will be removed.
     * @param previousVoiceChannel the new previous {@link AudioChannel}.
     */
    @Deprecated(since = "1.7.7", forRemoval = true)
    public void setPreviousVoiceChannel(AudioChannel previousVoiceChannel) {
        this.previousVoiceChannel = previousVoiceChannel;
    }

    /**
     * Get the current {@link AudioChannel} of the {@link Member}
     *
     * @return the current {@link AudioChannel}.
     */
    public AudioChannel getCurrentVoiceChannel() {
        return currentVoiceChannel;
    }

    /**
     * Change the current {@link AudioChannel} of the {@link Member}
     *
     * @deprecated Will be removed.
     * @param currentVoiceChannel the new current {@link AudioChannel}.
     */
    @Deprecated(since = "1.7.7", forRemoval = true)
    public void setCurrentVoiceChannel(AudioChannel currentVoiceChannel) {
        this.currentVoiceChannel = currentVoiceChannel;
    }
}
