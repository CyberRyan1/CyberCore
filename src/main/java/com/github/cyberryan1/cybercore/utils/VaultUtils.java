package com.github.cyberryan1.cybercore.utils;

import com.github.cyberryan1.cybercore.CyberCore;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultUtils {

    public static Permission vaultPermissions = null;
    public static Chat vaultChat = null;
    public static Economy vaultEconomy = null;

    public VaultUtils() {
        this( false );
    }

    public VaultUtils( boolean useEconomy ) {
        if ( ( setupPermissions() == false ) || ( setupChat() == false ) || ( useEconomy && setupEconomy() == false )) {
            CoreUtils.logError( "Disabled due to a Vault dependency error!" );
            CyberCore.getPlugin().getServer().getPluginManager().disablePlugin( CyberCore.getPlugin() );
            return;
        }
    }

    public static Permission getVaultPermissions() { return vaultPermissions; }

    public static Chat getVaultChat() { return vaultChat; }

    public static Economy getVaultEconomy() { return vaultEconomy; }

    // Check if an online player has a certain permission
    public static boolean hasPerms( Player player, String perm ) { return hasPerms( ( OfflinePlayer ) player, perm ); }

    // Check if a command sender has a certain permission
    public static boolean hasPerms( CommandSender sender, String perm ) {
        if ( sender instanceof OfflinePlayer ) {
            return hasPerms( ( OfflinePlayer ) sender, perm );
        }

        return vaultPermissions.has( sender, perm );
    }

    // Check if an offline player has a certain permission
    public static boolean hasPerms( OfflinePlayer player, String perm ) {
        if ( player.isOp() ) { return true; }

        if ( vaultPermissions.playerHas( null, player, "*" ) ) { return true; }
        return vaultPermissions.playerHas( null, player, perm );
    }

    // Get a player's prefix
    public static String getPlayerPrefix( Player player ) { return vaultChat.getPlayerPrefix( player ); }

    // Get a player's suffix
    public static String getPlayerSuffix( Player player ) { return vaultChat.getPlayerSuffix( player ); }

    // Get an offlineplayer's prefix in the first world
    public static String getPlayerPrefix( OfflinePlayer player ) {
        World world = CyberCore.getPlugin().getServer().getWorlds().get( 0 );
        return vaultChat.getPlayerPrefix( world.getName(), player );
    }

    // Get an offlineplayer's suffix in the first world
    public static String getPlayerSuffix( OfflinePlayer player ) {
        World world = CyberCore.getPlugin().getServer().getWorlds().get( 0 );
        return vaultChat.getPlayerSuffix( world.getName(), player );
    }

    private boolean setupPermissions() {
        if ( CyberCore.getPlugin().getServer().getPluginManager().getPlugin( "Vault" ) == null ) {
            return false;
        }

        RegisteredServiceProvider<Permission> rsp = CyberCore.getPlugin().getServer().getServicesManager().getRegistration( Permission.class );
        vaultPermissions = rsp.getProvider();
        return vaultPermissions != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = CyberCore.getPlugin().getServer().getServicesManager().getRegistration(Chat.class);
        vaultChat = rsp.getProvider();
        return vaultChat != null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = CyberCore.getPlugin().getServer().getServicesManager().getRegistration( Economy.class );
        vaultEconomy = rsp.getProvider();
        return vaultEconomy != null;
    }
}