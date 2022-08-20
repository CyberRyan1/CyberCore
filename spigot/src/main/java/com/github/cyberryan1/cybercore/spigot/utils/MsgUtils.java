package com.github.cyberryan1.cybercore.spigot.utils;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Predicate;

public class MsgUtils {

    /**
     * Sends the {@link ColorUtils#getColored(String ...)} of a given string list to a {@link CommandSender}
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public void sendMessage( CommandSender entity, String ... msgs ) {
        entity.sendMessage( CyberCore.COLOR_UTILS.getColored( msgs ) );
    }

    /**
     * Alias to the {@link #sendMessage(CommandSender, String...)} method
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public void sendMsg( CommandSender entity, String ... msgs ) {
        sendMessage( entity, msgs );
    }

    /**
     * Sends the {@link ColorUtils#getColored(String ...)} of a given string list to a {@link CommandSender}
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public void sendMessage( CommandSender entity, List<String> msgs ) {
        sendMessage( entity, msgs.toArray( new String[0] ) );
    }

    /**
     * Alias to the {@link #sendMessage(CommandSender, List)} method
     * @param entity The {@link CommandSender} to send the message to
     * @param msgs The strings to color
     */
    public void sendMsg( CommandSender entity, List<String> msgs ) {
        sendMessage( entity, msgs );
    }

    /**
     * Broadcasts a singular message
     * @param msg The message to broadcast
     */
    public void broadcast( String msg ) {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            sendMsg( player, msg );
        }
    }

    /**
     * Broadcasts multiple messages, each separated by a new line
     * @param msgs The messages to broadcast
     */
    public void broadcast( String ... msgs ) {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            sendMsg( player, msgs );
        }
    }

    /**
     * Broadcasts multiple messages, each separated by a new line
     * @param msgs The messages to broadcast
     */
    public void broadcast( List<String> msgs ) {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            sendMsg( player, msgs );
        }
    }

    /**
     * Broadcasts a singular message to all online players who
     * meet the given predicate
     * @param msg The message to broadcast
     * @param predicate The predicate to check against
     */
    public void broadcast( String msg, Predicate<? super Player> predicate ) {
        Bukkit.getOnlinePlayers().stream()
                .filter( predicate )
                .forEach( player -> sendMsg( player, msg ) );
    }

    /**
     * Broadcasts multiple messages, each separated by a new line,
     * to all online players who meet the given predicate
     * @param msgs The messages to broadcast
     * @param predicate The predicate to check against
     */
    public void broadcast( List<String> msgs, Predicate<? super Player> predicate ) {
        Bukkit.getOnlinePlayers().stream()
                .filter( predicate )
                .forEach( player -> sendMsg( player, msgs ) );
    }
}