package co.plasmanetwork.OPPrison.listeners;

import co.plasmanetwork.OPPrison.other.MinesLocations;
import co.plasmanetwork.OPPrison.OPPrison;
import co.plasmanetwork.OPPrison.managers.StringsManager;
import co.plasmanetwork.OPPrison.utils.Rewards;
import com.sk89q.minecraft.util.commands.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;

import static co.plasmanetwork.OPPrison.OPPrison.log;

/**
 * Created by Corey on 3/12/2016.
 */
public class PlayerListener implements Listener {
    public Economy econ = null;

    OPPrison plugin;

    public PlayerListener(OPPrison plugin) {
        this.plugin = plugin;
    }

    Random rand = new Random();
    StringsManager strings = StringsManager.getInstance();
    MinesLocations mLoc = MinesLocations.getInstance();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        String msg2 = msg.replace("%", "%%");
        String msg3 = msg2.replace("<3", "❤");
        Player p = e.getPlayer();
        String format = (e.getPlayer().getDisplayName() + " " + msg3);
        e.setFormat(format);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        if (p.hasPermission("OPPrison.Join")) {
            OPPrison.onlineFor.put(p.getPlayer(), 0);
            if (p.hasPermission("OPPrison.Join.Notify")) {
                Bukkit.broadcastMessage(strings.join + p.getName());
            }
            Bukkit.broadcastMessage(strings.defaultMsgs + ChatColor.GOLD + "Why hello there, " + ChatColor.RED + "" + ChatColor.BOLD + "" + p.getName() + ChatColor.GOLD + "!");
            log(p.getName() + " has joined successfully.");
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(null);
        if (p.hasPermission("OPPrison.Leave")) {
            OPPrison.onlineFor.remove(p.getPlayer());
            log(p.getName() + " has quit successfully.");
            if (p.hasPermission("OPPrison.Leave.Notify")) {
                Bukkit.broadcastMessage(strings.quit + p.getName());
            }
        }

    }

    // SPECIAL EFFECTS LISTENERS
//    @EventHandler
//    public void onPlaceBlock(PlayerInteractEvent e) {
//        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            Player p = e.getPlayer();
//            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world") || (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("mines"))) {
//                if (!(p.hasPermission("OPPrison.Build.Exempt"))) {
//                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You can't build in this world.");
//                    return;
//                }
//                return;
//            }
//            if (e.getPlayer().getItemInHand().getType().equals(Material.PURPUR_BLOCK)) {
//                if (p.getLocation().getWorld().getName().equals("world") || (p.getLocation().getWorld().getName().equals("mines"))) {
//
//                    if (p.hasPermission("OPPrison.Place.Exempt")) {
//                        return;
//                    }
//                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You can't place this block.");
//                    e.setCancelled(true);
//                }
//            }
//        }
//    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent e) {
        if (e.getPlayer().hasPermission("OPPrison.Effects.Use")) {
            Player p = e.getPlayer();
            ItemStack luckyPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            ItemMeta lpMeta = luckyPickaxe.getItemMeta();
            lpMeta.setDisplayName(ChatColor.GOLD + "Purrptastic Pickaxe");
            lpMeta.setLore(Arrays.asList(ChatColor.GREEN + "Use this Purrptastic Pickaxe to receive crazy prizes!", ChatColor.RED + "This can only be used on PurPur blocks."));
            lpMeta.addEnchant(Enchantment.ARROW_DAMAGE, 100, true);
            lpMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
            lpMeta.addEnchant(Enchantment.DAMAGE_ALL, 15, true);
            lpMeta.setUnbreakable(true);
            luckyPickaxe.setItemMeta(lpMeta);
            if (e.getBlock().getType().equals(Material.PURPUR_BLOCK)) {
                if (!(p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE))) {
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "Unfortunately, you require the Purrptastic Pickaxe to obtain this!");
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You can receive this from voting!");
                    e.setCancelled(true);
                    return;
                }
                if (!(p.getItemInHand().hasItemMeta())) {
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "Unfortunately, you require the Purrptastic Pickaxe to obtain this!");
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You can receive this from voting!");
                    e.setCancelled(true);
                    return;
                }
                if (!(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Purrptastic Pickaxe"))) {
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "Unfortunately, you require the Purrptastic Pickaxe to obtain this!");
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You can receive this from voting!");
                    e.setCancelled(true);
                    return;
                }
                if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Purrptastic Pickaxe")) {
                    if (p.getLocation().getWorld().getName().equals("world") || (p.getLocation().getWorld().getName().equals("mines"))) {
                        int chance = rand.nextInt(8); //0-8 (8)
                        if (chance == 0) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, 1));
                            Rewards.rewardMoney(p.getPlayer(), 750);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $750 from that block, as well as a potion effect!");
                            return;
                        }
                        if (chance == 1) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 14, 1));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
                            Rewards.rewardMoney(p.getPlayer(), 1250);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $1250 from that block, as well as two potion effects!");
                            return;
                        }
                        if (chance == 2) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 18, 2));
                            Rewards.rewardMoney(p.getPlayer(), 1500);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $1500 from that block, as well as a potion effect!");
                            return;
                        }
                        if (chance == 3) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 22, 2));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 22, 2));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 22, 1));
                            Rewards.rewardMoney(p.getPlayer(), 2250);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $2250 from that block, as well as three potion effects!");
                            return;
                        }
                        if (chance == 4) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 26, 4));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 26, 3));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 26, 1));
                            Rewards.rewardMoney(p.getPlayer(), 2800);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $2800 from that block, as well as three potion effects!");
                            return;
                        }
                        if (chance == 5) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 30, 7));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 30, 3));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 2));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 30, 2));
                            Rewards.rewardMoney(p.getPlayer(), 3100);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $3100 from that block, as well as four potion effects!");
                            return;
                        }
                        if (chance == 7) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 34, 6));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 34, 6));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 34, 4));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 34, 3));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 34, 1));
                            Rewards.rewardMoney(p.getPlayer(), 4500);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $4500 from that block, as well as five potion effects!");
                            return;
                        }
                        if (chance == 8) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 40, 8));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 34, 8));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 34, 4));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 34, 4));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 34, 2));
                            Rewards.rewardMoney(p.getPlayer(), 5000);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $5000 from that block, as well as five potion effects!");
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, 1));
                            OPPrison.econ.depositPlayer(p, 750);
                            ItemStack air = new ItemStack(Material.AIR, 1);
                            e.getBlock().getDrops().add(air);
                            p.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "Congratulations, you earned $750 from that block, as well as a potion effect!");
                        }
                    }
                } else {
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "Unfortunately, you require the Purrptastic Pickaxe to obtain this!");
                    p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You can receive this from voting!");
                    e.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onPurPurPickBreak(PlayerItemBreakEvent e) {
        ItemStack luckyPickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta lpMeta = luckyPickaxe.getItemMeta();
        lpMeta.setDisplayName(ChatColor.GOLD + "Purrptastic Pickaxe");
        lpMeta.setLore(Arrays.asList(ChatColor.GREEN + "Use this Purrptastic Pickaxe to receive random crazy prizes!", ChatColor.RED + "This can only be used on PurPur blocks."));
        lpMeta.addEnchant(Enchantment.ARROW_DAMAGE, 100, true);
        lpMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        lpMeta.addEnchant(Enchantment.DAMAGE_ALL, 15, true);
        luckyPickaxe.setItemMeta(lpMeta);
        if (e.getBrokenItem().equals(luckyPickaxe)) {
            e.getPlayer().getItemInHand().setDurability((short) -10);
        }
    }
}
// this goes in the PlayerJoinEvent. \\
//            if (OPPrison.messageData.get("joinMessage") == null) {
//                log("ERROR: JOIN MESSAGE HAS NOT BEEN SET IN MESSAGES.YML! Set 'joinMessage'!");
//            } else {
//                p.sendMessage(ChatColor.translateAlternateColorCodes('&', OPPrison.messageData.get("joinMessage")));
//            }
//            if (OPPrison.messageData.get("joinNotifyMessage") == null) {
//                log("ERROR: JOIN NOTIFY MESSAGE HAS NOT BEEN SET IN THE MESSAGES.YML! Set 'joinNotifyMessage'!");
//            } else {
//                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', OPPrison.messageData.get("joinNotifyMessage")));
//            }