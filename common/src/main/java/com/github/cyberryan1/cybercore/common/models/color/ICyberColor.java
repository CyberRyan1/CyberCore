package com.github.cyberryan1.cybercore.common.models.color;

import java.util.List;

/**
 * The ICyberColor class is an interface.
 * It is used to color messages and keep track of primary and secondary colors.
 *
 * @author CyberRyan1
 */
public interface ICyberColor {

    /**
     * Sets the primary color for the plugin.
     * This is what "&p" within strings will be replaced with.
     * @param primaryColor The primary color <i>(color code, like "&b")</i>
     */
    void setPrimaryColor( String primaryColor );

    /**
     * Sets the secondary color for the plugin.
     * This is what "&s" within strings will be replaced with.
     * @param secondaryColor The secondary color <i>(color code, like "&7")</i>
     */
    void setSecondaryColor( String secondaryColor );

    /**
     * @return The primary color for the plugin <i>(color code, like "&b")</i>
     */
    String getPrimaryColor();

    /**
     * @return The secondary color for the plugin <i>(color code, like "&7")</i>
     */
    String getSecondaryColor();

    /**
     * Gets the colored string of a given string
     * @param msg The string to color
     * @return The colored string
     */
    String getColored( String msg );

    /**
     * Gets the colored strings of a given string list
     * @param msgs The strings to color
     * @return The colored strings
     */
    String[] getColored( String ... msgs );

    /**
     * Gets the colored strings of a given string list
     * @param msgs The {@link List} of strings to color
     * @return The colored strings
     */
    List<String> getColored( List<String> msgs );

    /**
     * Reverses the colors of a message into the respective color codes.
     * @param msg The message to reverse the colors of
     * @return The reversed message
     */
    String reverseColor( String msg );

    /**
     * Removes all color from a message
     * @param msg The message to remove color from
     * @return The message without color
     */
    String deleteColor( String msg );

    /**
     * Removes all color <b>AND</b> color codes from a message
     * @param msg The message to remove color from
     * @return The message without color nor color codes
     */
    default String getUncolored( String msg ) {
        return deleteColor( getColored( msg ) );
    }
}