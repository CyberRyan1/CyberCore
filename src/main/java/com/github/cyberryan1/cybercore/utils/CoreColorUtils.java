package com.github.cyberryan1.cybercore.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Thanks to Kody Simpson for the original code.
 * Link <a href="https://gitlab.com/kody-simpson/spigot/1.16-color-translator/-/blob/master/ColorUtils.java">here</a>
 */
public class CoreColorUtils {

    private static final String WITH_DELIM = "((?<=%1$s)|(?=%1$s))";

    public static String getColored( String text ) {
        String split[] = text.split( String.format( WITH_DELIM, "&" ) );

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
        }

        return toReturn.toString();
    }

    public static TextComponent getColoredComponent( String text ) {
        String split[] = text.split( String.format( WITH_DELIM, "&" ) );

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
                        case '0':
                            subComponent.setColor( ChatColor.BLACK );
                            break;
                        case '1':
                            subComponent.setColor( ChatColor.DARK_BLUE );
                            break;
                        case '2':
                            subComponent.setColor( ChatColor.DARK_GREEN );
                            break;
                        case '3':
                            subComponent.setColor( ChatColor.DARK_AQUA );
                            break;
                        case '4':
                            subComponent.setColor( ChatColor.DARK_RED );
                            break;
                        case '5':
                            subComponent.setColor( ChatColor.DARK_PURPLE );
                            break;
                        case '6':
                            subComponent.setColor( ChatColor.GOLD );
                            break;
                        case '7':
                            subComponent.setColor( ChatColor.GRAY );
                            break;
                        case '8':
                            subComponent.setColor( ChatColor.DARK_GRAY );
                            break;
                        case '9':
                            subComponent.setColor( ChatColor.BLUE );
                            break;
                        case 'a':
                            subComponent.setColor( ChatColor.GREEN );
                            break;
                        case 'b':
                            subComponent.setColor( ChatColor.AQUA );
                            break;
                        case 'c':
                            subComponent.setColor( ChatColor.RED );
                            break;
                        case 'd':
                            subComponent.setColor( ChatColor.LIGHT_PURPLE );
                            break;
                        case 'e':
                            subComponent.setColor( ChatColor.YELLOW );
                            break;
                        case 'f':
                            subComponent.setColor( ChatColor.WHITE );
                            break;
                        case 'k':
                            subComponent.setObfuscated( true );
                            break;
                        case 'l':
                            subComponent.setBold( true );
                            break;
                        case 'm':
                            subComponent.setStrikethrough( true );
                            break;
                        case 'n':
                            subComponent.setUnderlined( true );
                            break;
                        case 'o':
                            subComponent.setItalic( true );
                            break;
                        case 'r':
                            subComponent.setColor( ChatColor.RESET );
                            break;
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