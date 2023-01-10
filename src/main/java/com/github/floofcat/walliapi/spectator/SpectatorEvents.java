package com.github.floofcat.walliapi.spectator;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SpectatorEvents implements Listener {

    private final WalliAPI plugin;

    private Inventory inventory2;
    private Inventory inventory3;
    private Inventory inventory;

    public SpectatorEvents(WalliAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void foodEvents(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        WalliPlayer wp = this.plugin.getWalliPlayer((Player) event.getEntity());

        if(wp.isSpectator()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        WalliPlayer wp = this.plugin.getWalliPlayer(event.getPlayer());

        if(!wp.isSpectator()) {
            return;
        }

        // Compass
        event.setCancelled(true);

        ItemStack interactedItem = event.getItem();
        if(interactedItem.getType().equals(Material.COMPASS)) {
            ItemMeta itemMeta = interactedItem.getItemMeta();
            if(itemMeta.getCustomModelData() == 10000) {
                this.inventory = Bukkit.createInventory(wp.getPlayer(), 9*6, "Player Teleporter - Page 1");
                this.inventory2 = Bukkit.createInventory(wp.getPlayer(), 9*6, "Player Teleporter - Page 2");
                this.inventory3 = Bukkit.createInventory(wp.getPlayer(), 9*6, "Player Teleporter - Page 3");

                List<WalliPlayer> alivePlayers = new ArrayList<>();
                for(WalliPlayer walliPlayer : this.plugin.getTeamController().getPlayers()) {
                    if(!walliPlayer.isValid()) {
                        continue;
                    }

                    if(walliPlayer.isSpectator()) {
                        continue;
                    }

                    alivePlayers.add(walliPlayer);
                }

                List<WalliPlayer> pageOne = new ArrayList<>();
                List<WalliPlayer> pageTwo = new ArrayList<>();
                List<WalliPlayer> pageThree = new ArrayList<>();
                int i = 1;
                for(WalliPlayer walliPlayer : alivePlayers) {
                    i++;
                    if(i < 36) {
                        pageOne.add(walliPlayer);
                    } else if (36 <= i && i <= 72) {
                        pageTwo.add(walliPlayer);
                    } else if (i >= 72) {
                        pageThree.add(walliPlayer);
                    }
                }

                for(WalliPlayer page1 : pageOne) {
                    ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
                    skullMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(page1.getPlayer().getName()));

                    skullMeta.setOwningPlayer(page1.getPlayer());
                    skullItem.setItemMeta(skullMeta);

                    inventory.addItem(skullItem);

                    ItemStack nextPage = new ItemStack(Material.GREEN_WOOL, 1);
                    ItemMeta nextMeta = nextPage.getItemMeta();
                    nextMeta.setCustomModelData(10000);
                    nextMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(ChatColor.GREEN + "Next Page"));
                    inventory.setItem(53, nextPage);
                }

                for(WalliPlayer page2 : pageTwo) {
                    ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
                    skullMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(page2.getPlayer().getName()));

                    skullMeta.setOwningPlayer(page2.getPlayer());
                    skullItem.setItemMeta(skullMeta);

                    inventory2.addItem(skullItem);

                    ItemStack prevPage = new ItemStack(Material.RED_WOOL, 1);
                    ItemMeta prevMeta = prevPage.getItemMeta();
                    prevMeta.setCustomModelData(10001);
                    prevMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(ChatColor.RED + "Previous Page"));
                    inventory2.setItem(45, prevPage);

                    ItemStack nextPage = new ItemStack(Material.GREEN_WOOL, 1);
                    ItemMeta nextMeta = nextPage.getItemMeta();
                    nextMeta.setCustomModelData(10002);
                    nextMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(ChatColor.GREEN + "Next Page"));
                    inventory2.setItem(53, nextPage);
                }

                for(WalliPlayer page3 : pageThree) {
                    ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
                    skullMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(page3.getPlayer().getName()));

                    skullMeta.setOwningPlayer(page3.getPlayer());
                    skullItem.setItemMeta(skullMeta);

                    inventory3.addItem(skullItem);

                    ItemStack nextPage = new ItemStack(Material.RED_WOOL, 1);
                    ItemMeta nextMeta = nextPage.getItemMeta();
                    nextMeta.setCustomModelData(10003);
                    nextMeta.displayName(PlainTextComponentSerializer.plainText().
                            deserialize(ChatColor.RED + "Previous Page"));
                    inventory3.setItem(45, nextPage);
                }
            }
        }

    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        if(event.getClickedInventory().equals(inventory)) {
            event.setCancelled(true);
            if(event.getCursor().getType().equals(Material.PLAYER_HEAD)) {
                SkullMeta meta = (SkullMeta) event.getCursor().getItemMeta();
                Player player = meta.getOwningPlayer().getPlayer();

                event.getWhoClicked().teleport(player.getLocation());
                event.getWhoClicked().closeInventory();
            }

            if(event.getCursor().getType().equals(Material.GREEN_WOOL)) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(inventory2);
            }
        }

        if(event.getClickedInventory().equals(inventory2)) {
            event.setCancelled(true);
            if(event.getCursor().getType().equals(Material.PLAYER_HEAD)) {
                SkullMeta meta = (SkullMeta) event.getCursor().getItemMeta();
                Player player = meta.getOwningPlayer().getPlayer();

                event.getWhoClicked().teleport(player.getLocation());
                event.getWhoClicked().closeInventory();
            }

            if(event.getCursor().getType().equals(Material.GREEN_WOOL)) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(inventory3);
            }

            if(event.getCursor().getType().equals(Material.RED_WOOL)) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(inventory);
            }
        }

        if(event.getClickedInventory().equals(inventory3)) {
            event.setCancelled(true);
            if(event.getCursor().getType().equals(Material.PLAYER_HEAD)) {
                SkullMeta meta = (SkullMeta) event.getCursor().getItemMeta();
                Player player = meta.getOwningPlayer().getPlayer();

                event.getWhoClicked().teleport(player.getLocation());
                event.getWhoClicked().closeInventory();
            }

            if(event.getCursor().getType().equals(Material.RED_WOOL)) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockBreakEvent event) {
        WalliPlayer wp = this.plugin.getWalliPlayer(event.getPlayer());

        if(wp.isSpectator()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void pickupEvent(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        WalliPlayer wp = this.plugin.getWalliPlayer(player);

        if(wp.isSpectator()) {
            event.setCancelled(true);
        }
    }
}