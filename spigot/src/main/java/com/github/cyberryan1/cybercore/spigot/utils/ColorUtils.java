package com.github.cyberryan1.cybercore.spigot.utils;

import com.github.cyberryan1.cybercore.common.models.color.ICyberColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/**
 * Manages color translations within spigot. <p>
 *
 * Thanks to Kody Simpson for the {@link ColorUtils#getColored( String )}
 * and {@link ColorUtils#getColoredComponent( String )} methods. Link to
 * the original code can be found <a href="https://gitlab.com/kody-simpson/spigot/1.16-color-translator/-/blob/master/ColorUtils.java">here</a>
 *
 * @author CyberRyan1 and Kody Simpson
 */
public class ColorUtils implements ICyberColor {

    private static final String DELIM = "((?<=%1$s)|(?=%1$s))";

    private String primaryColor = "&b";
    private String secondaryColor = "&7";

    @Override
    public void setPrimaryColor( String primaryColor ) {
        this.primaryColor = primaryColor;
    }

    @Override
    public void setSecondaryColor( String secondaryColor ) {
        this.secondaryColor = secondaryColor;
    }

    @Override
    public String getPrimaryColor() {
        return this.primaryColor;
    }

    @Override
    public String getSecondaryColor() {
        return this.secondaryColor;
    }

    @Override
    public String getColored( String msg ) {
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

    @Override
    public String[] getColored( String... msgs ) {
        return new String[0];
    }

    @Override
    public List<String> getColored( List<String> msgs ) {
        return null;
    }

    @Override
    public String reverseColor( String msg ) {
        return null;
    }

    @Override
    public String deleteColor( String msg ) {
        return null;
    }

    public TextComponent getColoredComponent( String msg ) {
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