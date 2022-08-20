package com.github.cyberryan1.cybercore.spigot.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/**
 * Manages color translations within spigot. <p>
 *
 * Thanks to Kody Simpson for the {@link CyberColorUtils#getColored( String )}
 * and {@link CyberColorUtils#getColoredComponent( String )} methods. Link to
 * the original code can be found <a href="https://gitlab.com/kody-simpson/spigot/1.16-color-translator/-/blob/master/ColorUtils.java">here</a>
 *
 * @author CyberRyan1 and Kody Simpson
 */
public class CyberColorUtils {

    private static final String DELIM = "((?<=%1$s)|(?=%1$s))";
    private static String primaryColor = "&b";
    private static String secondaryColor = "&7";

    /**
     * Sets the primary color for the plugin.
     * This is what "&p" within strings will be replaced with.
     * @param color The primary color <i>(color code, like "&b")</i>
     */
    public static void setPrimaryColor( String color ) {
        primaryColor = color;
    }

    /**
     * Sets the secondary color for the plugin.
     * This is what "&s" within strings will be replaced with.
     * @param color The secondary color <i>(color code, like "&7")</i>
     */
    public static void setSecondaryColor( String color ) {
        secondaryColor = color;
    }

    /**
     * @return The primary color for the plugin <i>(color code, like "&b")</i>
     */
    public static String getPrimaryColor() {
        return primaryColor;
    }

    /**
     * @return The secondary color for the plugin <i>(color code, like "&7")</i>
     */
    public static String getSecondaryColor() {
        return secondaryColor;
    }

    /**
     * Gets the colored string of a given string
     * @param msg The string to color
     * @return The colored string
     */
    public static String getColored( String msg ) {
        msg = msg.replace( "&p", primaryColor ).replace( "&s", secondaryColor );
        String split[] = msg.split( String.format( DELIM, "&" ) );

        StringBuilder toReturn = new StringBuilder();
        for ( int index = 0; index < split.length; index++ ) {
            if ( split[index].equalsIgnoreCase( "&" ) ) {
                index++;
                if ( split[index].charAt( 0 ) == '#' ) {
                    toReturn.append( ChatColor.of( split[index].substring( 0, 7 ) ) + split[index].substring( 7 ) );
                }
                else {
                    toReturn.append( ChatColor.translateAlternateColorCodes( '&', '&' + split[index] ) );
                }
            }
            else {
                toReturn.append( split[index] );
            }
        }

        return toReturn.toString();
    }

    /**
     * Gets the colored strings of a given string list
     * @param msgs The strings to color
     * @return The colored strings
     */
    public static String[] getColored( String... msgs ) {
        String[] toReturn = new String[msgs.length];
        for ( int index = 0; index < msgs.length; index++ ) {
            toReturn[index] = getColored( msgs[index] );
        }
        return toReturn;
    }

    /**
     * Gets the colored strings of a given string list
     * @param msgs The {@link List} of strings to color
     * @return The colored strings
     */
    public static List<String> getColored( List<String> msgs ) {
        msgs.replaceAll( CyberColorUtils::getColored );
        return msgs;
    }

    /**
     * Reverses the colors of a message into the respective color codes.
     * @param msg The message to reverse the colors of
     * @return The reversed message
     */
    public static String reverseColor( String msg ) {
        final String COLOR_CODES[] = { "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&0",
                "&a", "&b", "&c", "&d", "&e", "&f", "&r", "&m", "&n", "&o", "&k", "&l" };

        for ( String str : COLOR_CODES ) {
            msg = msg.replaceAll( getColored( str ), str );
        }
        return msg;
    }

    /**
     * Removes all color from a message
     * @param msg The message to remove color from
     * @return The message without color
     */
    public static String deleteColor( String msg ) {
        return ChatColor.stripColor( msg );
    }

    /**
     * Removes all color <b>AND</b> color codes from a message
     * @param msg The message to remove color from
     * @return The message without color nor color codes
     */
    public static String getUncolored( String msg ) {
        return deleteColor( getColored( msg ) );
    }

    /**
     * Gets the colored {@link TextComponent} of a given string
     * @param msg The string to color
     * @return The colored {@link TextComponent}
     */
    public static TextComponent getColoredComponent( String msg ) {
        String split[] = msg.split( String.format( DELIM, "&" ) );

        ComponentBuilder builder = new ComponentBuilder();

        for ( int index = 0; index < split.length; index++ ) {
            TextComponent subComponent = new TextComponent();
            if ( split[index].equalsIgnoreCase( "&" ) ) {
                //get the next string
                index++;
                if ( split[index].charAt( 0 ) == '#' ) {
                    subComponent.setText( split[index].substring( 7 ) );
                    subComponent.setColor( ChatColor.of( split[index].substring( 0, 7 ) ) );
                    builder.append( subComponent );
                }

                else {
                    if ( split[index].length() > 1 ) {
                        subComponent.setText( split[index].substring( 1 ) );
                    }

                    else{
                        subComponent.setText( " " );
                    }

                    switch ( split[index].charAt( 0 ) ) {
                        case '0' -> subComponent.setColor( ChatColor.BLACK );
                        case '1' -> subComponent.setColor( ChatColor.DARK_BLUE );
                        case '2' -> subComponent.setColor( ChatColor.DARK_GREEN );
                        case '3' -> subComponent.setColor( ChatColor.DARK_AQUA );
                        case '4' -> subComponent.setColor( ChatColor.DARK_RED );
                        case '5' -> subComponent.setColor( ChatColor.DARK_PURPLE );
                        case '6' -> subComponent.setColor( ChatColor.GOLD );
                        case '7' -> subComponent.setColor( ChatColor.GRAY );
                        case '8' -> subComponent.setColor( ChatColor.DARK_GRAY );
                        case '9' -> subComponent.setColor( ChatColor.BLUE );
                        case 'a' -> subComponent.setColor( ChatColor.GREEN );
                        case 'b' -> subComponent.setColor( ChatColor.AQUA );
                        case 'c' -> subComponent.setColor( ChatColor.RED );
                        case 'd' -> subComponent.setColor( ChatColor.LIGHT_PURPLE );
                        case 'e' -> subComponent.setColor( ChatColor.YELLOW );
                        case 'f' -> subComponent.setColor( ChatColor.WHITE );
                        case 'k' -> subComponent.setObfuscated( true );
                        case 'l' -> subComponent.setBold( true );
                        case 'm' -> subComponent.setStrikethrough( true );
                        case 'n' -> subComponent.setUnderlined( true );
                        case 'o' -> subComponent.setItalic( true );
                        case 'r' -> subComponent.setColor( ChatColor.RESET );
                    }

                    builder.append( subComponent );
                }
            }

            else{
                builder.append( split[index] );
            }
        }

        return new TextComponent( builder.create() );
    }
}