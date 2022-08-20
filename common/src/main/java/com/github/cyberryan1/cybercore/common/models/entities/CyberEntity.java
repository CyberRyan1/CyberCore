package com.github.cyberryan1.cybercore.common.models.entities;

/**
 * The CyberEntity class is a final class.
 * It is used to simplify the tracking of entity types between multiple different server types.
 *
 * @param <E> The type of the entity
 * @author CyberRyan1
 */
public final class CyberEntity<E> {

    private final E entity;

    /**
     * Creates a new CyberEntity object with the given entity
     * @param entity The command sender
     */
    public CyberEntity( E entity ) {
        this.entity = entity;
    }

    /**
     * @return The entity
     */
    public E getEntity() {
        return this.entity;
    }
}