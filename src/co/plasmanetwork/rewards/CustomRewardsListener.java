package co.plasmanetwork.rewards;

import co.plasmanetwork.OPPrison;
import co.plasmanetwork.managers.StringsManager;
import co.plasmanetwork.utils.Rewards;
import com.sk89q.minecraft.util.commands.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent; // have fun, wat do you want me to do? xD LOOK AT THE CODE KUNT, oh
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Corey on 5/12/2016.
 */
public class CustomRewardsListener implements Listener {

    static CustomRewardsListener instance = new CustomRewardsListener();
    OPPrison plugin;

    public CustomRewardsListener(OPPrison plugin) {
        this.plugin = plugin;
    }

    Random rand = new Random();

    public static CustomRewardsListener getInstance() {
        return instance;
    }

    private CustomRewardsListener() {

    }

    StringsManager strings = StringsManager.getInstance();


}
