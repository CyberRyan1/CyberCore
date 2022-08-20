package com.github.cyberryan1.cybercore.common.models;

/**
 * The CyberSender class is a final class.
 * It is used to simplify the tracking of command sender types.
 *
 * @param <S> The type of the command sender
 * @author CyberRyan1
 */
public final class CyberSender<S> {

    private final S sender;

    /**
     * Creates a new CyberSender object with the given command sender.
     * @param sender The command sender
     */
    public CyberSender( S sender ) {
        this.sender = sender;
    }

    /**
     * @return The command sender
     */
    public S getSender() {
        return this.sender;
    }
}