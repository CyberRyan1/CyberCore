package com.github.cyberryan1.cybercore.spigot.utils;

import com.github.cyberryan1.cybercore.spigot.CyberCore;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Vault utilities
 *
 * @author CyberRyan1
 */
public class CyberVaultUtils {

    public static Permission vaultPermissions = null;
    public static Chat vaultChat = null;
    public static Economy vaultEconomy = null;

    /**
     * Initializes Vault permissions and chat
     */
    public CyberVaultUtils() {
        this( false );
    }

    /**
     * Initializes Vault permissions and chat. If useEconomy is true, Vault
     * economy is initialized as well.
     * @param useEconomy Whether to initialize Vault economy
     */
    public CyberVaultUtils( boolean useEconomy ) {
        if ( ( setupPermissions() == false ) || ( setupChat() == false ) || ( useEconomy && setupEconomy() == false ) ) {
            CyberLogUtils.logError( "Disabled due to a Vault dependency error!" );
            CyberCore.getPlugin().getServer().getPluginManager().disablePlugin( CyberCore.getPlugin() );
        }
    }

    /**
     * @return The Vault permissions object
     */
    public static Permission getVaultPermissions() {
        return vaultPermissions;
    }

    /**
     * @return The Vault chat object
     */
    public static Chat getVaultChat() {
        return vaultChat;
    }

    /**
     * @return The Vault economy object
     */
    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }

    /**
     * Checks if the given player has the given permission
     * @param player The {@link Player} to check
     * @param perm The permission
     * @return True if the player has the permission, false otherwise
     */
    public static boolean hasPerms( Player player, String perm ) {
        return hasPerms( ( OfflinePlayer ) player, perm );
    }

    /**
     * Checks if a command sender has a certain permission
     * @param sender The {@link CommandSender} to check
     * @param perm The permission
     * @return True if the sender has the permission, false otherwise
     */
    public static boolean hasPerms( CommandSender sender, String perm ) {
        if ( sender instanceof OfflinePlayer ) {
            return hasPerms( ( OfflinePlayer ) sender, perm );
        }

        return vaultPermissions.has( sender, perm );
    }

    /**
     * Checks if an offline player has a certain permission
     * @param player The {@link OfflinePlayer} to check
     * @param perm The permission
     * @return True if the player has the permission, false otherwise
     */
    public static boolean hasPerms( OfflinePlayer player, String perm ) {
        if ( player.isOp() ) {
            return true;
        }

        if ( vaultPermissions.playerHas( null, player, "*" ) ) {
            return true;
        }
        return vaultPermissions.playerHas( null, player, perm );
    }

    /**
     * Gets a player's prefix
     * @param player The {@link Player} to get the prefix of
     * @return The player's prefix
     */
    public static String getPlayerPrefix( Player player ) {
        return vaultChat.getPlayerPrefix( player );
    }

    /**
     * Gets a player's suffix
     * @param player The {@link Player} to get the suffix of
     * @return The player's suffix
     */
    public static String getPlayerSuffix( Player player ) {
        return vaultChat.getPlayerSuffix( player );
    }

    /**
     * Gets an offline player's prefix
     * @param player The {@link OfflinePlayer} to get the prefix of
     * @return The player's prefix
     */
    public static String getPlayerPrefix( OfflinePlayer player ) {
        World world = CyberCore.getPlugin().getServer().getWorlds().get( 0 );
        return vaultChat.getPlayerPrefix( world.getName(), player );
    }

    /**
     * Gets an offline player's suffix
     * @param player The {@link OfflinePlayer} to get the suffix of
     * @return The player's suffix
     */
    public static String getPlayerSuffix( OfflinePlayer player ) {
        World world = CyberCore.getPlugin().getServer().getWorlds().get( 0 );
        return vaultChat.getPlayerSuffix( world.getName(), player );
    }

    /**
     * Sets up the Vault permissions object
     * @return True if the setup was successful, false otherwise
     */
    private boolean setupPermissions() {
        if ( CyberCore.getPlugin().getServer().getPluginManager().getPlugin( "Vault" ) == null ) {
            return false;
        }

        RegisteredServiceProvider<Permission> rsp = CyberCore.getPlugin().getServer().getServicesManager().getRegistration( Permission.class );
        vaultPermissions = rsp.getProvider();
        return vaultPermissions != null;
    }

    /**
     * Sets up the Vault chat object
     * @return True if the setup was successful, false otherwise
     */
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = CyberCore.getPlugin().getServer().getServicesManager().getRegistration( Chat.class );
        vaultChat = rsp.getProvider();
        return vaultChat != null;
    }

    /**
     * Sets up the Vault economy object
     * @return True if the setup was successful, false otherwise
     */
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = CyberCore.getPlugin().getServer().getServicesManager().getRegistration( Economy.class );
        vaultEconomy = rsp.getProvider();
        return vaultEconomy != null;
    }
}