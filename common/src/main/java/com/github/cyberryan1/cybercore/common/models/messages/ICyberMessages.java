package com.github.cyberryan1.cybercore.common.models.messages;

import com.github.cyberryan1.cybercore.common.models.entities.CyberEntity;

import java.util.List;
import java.util.function.Predicate;

/**
 * The ICyberMessages class is an interface.
 * It's used to send and broadcast messages to players.
 *
 * @author CyberRyan1
 */
public interface ICyberMessages {

    /**
     * Sends the {@link ICyberColor#getColored( String ...)} of a given string list to a {@link CyberEntity}
     * @param entity The {@link CyberEntity} to send the message to
     * @param msgs The strings to send
     */
    default void sendMessage( CyberEntity<?> entity, String ... msgs ) {
        sendMessage( entity, List.of( msgs ) );
    }

    /**
     * Sends the {@link ICyberColor#getColored( String ...)} of a given string list to a {@link CyberEntity}
     * @param entity The {@link CyberEntity} to send the message to
     * @param msgs The {@link List} of strings to send
     */
    void sendMessage( CyberEntity<?> entity, List<String> msgs );

    /**
     * Alias to the {@link #sendMessage( CyberEntity, String... )} method
     * @param entity The {@link CyberEntity} to send the message to
     * @param msgs The strings to send
     */
    default void sendMsg( CyberEntity<?> entity, String ... msgs ) {
        sendMsg( entity, List.of( msgs ) );
    }

    /**
     * Alias to the {@link #sendMessage( CyberEntity, String... )} method
     * @param entity The {@link CyberEntity} to send the message to
     * @param msgs The {@link List} of strings to send
     */
    void sendMsg( CyberEntity<?> entity, List<String> msgs );

    /**
     * Broadcasts the {@link ICyberColor#getColored( String ... )} of a given string list to all players
     * @param msgs The strings to send
     */
    default void broadcast( String ... msgs ) {
        broadcast( List.of( msgs ) );
    }

    /**
     * Broadcasts the {@link ICyberColor#getColored( List )} of a given string list to all players
     * @param msgs The {@link List} of strings to send
     */
    void broadcast( List<String> msgs );

    /**
     * Broadcasts the {@link ICyberColor#getColored( String )} of a given string
     * to all players that match the given {@link Predicate}
     * @param msg The string to send
     * @param predicate The {@link Predicate} to match against
     */
    void broadcast( String msg, Predicate<? super CyberEntity<?>> predicate );

    /**
     * Broadcasts the {@link ICyberColor#getColored( String )} of a given string list
     * to all players that match the given {@link Predicate}
     * @param msgs The {@link List} of strings to send
     * @param predicate The {@link Predicate} to match against
     */
    void broadcast( List<String> msgs, Predicate<? super CyberEntity<?>> predicate );
}