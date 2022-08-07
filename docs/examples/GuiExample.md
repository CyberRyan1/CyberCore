
# GUI Example

This is an example of how GUIs are created, how to use them, etc. Obviously some 
things may need to be changed, depending on the project, what you need accomplished, etc.

```java
// Imports
import com.github.cyberryan1.cybercore.helpers.gui.GUI;
import com.github.cyberryan1.cybercore.helpers.gui.GUIItem;
import com.github.cyberryan1.cybercore.utils.CoreGUIUtils;
import com.github.cyberryan1.cybercore.utils.CoreItemUtils;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerActionListener implements Listener {

    // In this event, we are going to wait for a player to right-click on 
    //      an oak sign. When they do so, a GUI will open for them.

    @EventHandler
    public void onPlayerInteract( final PlayerInteractEvent interactEvent ) {
        // Checking if the action is a right click on a block
        if ( interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            // Checking if the clicked block was a sign
            if ( interactEvent.getClickedBlock().getType() == Material.OAK_SIGN ) {
                // Now, we are going to create a GUI for the player.

                final GUI gui = new GUI(
                        // The name of the GUI
                        "&6&lGUI Example",
                        // The number of rows in the GUI
                        3,
                        // The default block to use for all the slots in the GUI.
                        //      Note that this is an optional argument.
                        CoreGUIUtils.getBackgroundGlass()
                );

                // Now, we are going to add some items to the GUI.

                // Creating a GUI item
                GUIItem itemOne = new GUIItem(
                        Material.STONE, // The material of the item
                        "&6Stone Block", // The name of the item
                        4 // The index of the GUI for the item
                );
                // Adding the item to the GUI
                gui.addItem( itemOne.getSlot(), itemOne );

                // Creates an item with a custom name
                ItemStack itemToAdd = CoreItemUtils.createItem(
                        Material.REDSTONE, // The material of the item
                        "&cA Random Redstone..." // The name of the item
                );
                // Adding a lore to the item
                itemToAdd = CoreItemUtils.addItemLore( itemToAdd, "&7Definitely not suspicious..." );

                GUIItem itemTwo = new GUIItem(
                        itemToAdd, // The item to add to the GUI
                        14, // The index of the GUI for the item
                        () -> {
                            // This is the action to perform when the item is clicked. In
                            //      this case, we are going to send the player a message.
                            //      If no action is specified, the item will do nothing.
                            interactEvent.getPlayer().closeInventory();
                            CoreUtils.sendMsg( interactEvent.getPlayer(), "&aYou clicked on the item!" );
                        }
                );
                gui.addItem( itemTwo.getSlot(), itemTwo );
                
                // Creates another item with a custom name
                GUIItem itemThree = new GUIItem(
                        Material.DIAMOND_SHOVEL, // The material of the item
                        "&bA Diamond Shovel", // The name of the item
                        19,
                        ( guiItem ) -> {
                            // This is similar to the above action, but this time, we are
                            //      given the GUIItem that was clicked.
                            
                            // In this event, we are updating the type of the item to a 
                            //      diamond pickaxe and changing the name. 
                            guiItem.setItem( Material.DIAMOND_PICKAXE, "&bA Diamond PIckaxe" );
                            // This is how we update the item in the GUI
                            gui.updateItem( guiItem );
                        }
                );
                // Note that we don't have to specify the slot of the item
                gui.addItem( itemThree );
                
                // What to execute when the GUI is closed
                // You don't have to add this, but you can if needed
                gui.setCloseAction( () -> {
                    CoreUtils.sendMsg( interactEvent.getPlayer(), "&aYou close the GUI!" );
                } );

                // Creating the GUI
                gui.createInventory();
                // Opening the inventory for the player
                gui.openInventory( interactEvent.getPlayer() );
                // Note: the above two statements can be replaced with the following:
                //      gui.createAndOpen( interactEvent.getPlayer() );

            }
        }
    }
}
```