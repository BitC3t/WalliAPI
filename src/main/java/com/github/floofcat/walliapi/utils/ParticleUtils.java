package com.github.floofcat.walliapi.utils;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin;
import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ParticleUtils {

    private static ParticleNativeAPI api;

    public static void init() {
        api = ParticleNativeCore.loadAPI(WalliAPI.getInstance());
    }

    public static void spawnCircleParticles(Location location, int radius) {
        double density = 0.1;

        for(int a = radius; a > 0; a-=2) {
            for(double i = 0; i < 2*Math.PI; i += density) {
                double x = Math.cos(i) * a;
                double z = Math.sin(i) * a;

                Location newLoc = new Location(location.getWorld(),
                        location.getX() + x, location.getY(), location.getZ() + z);

                Bukkit.getOnlinePlayers().forEach(player -> {
                    api.LIST_1_8.REDSTONE
                            .packet(true, newLoc)
                            .sendTo(player);
                });
            }
        }
    }
}
