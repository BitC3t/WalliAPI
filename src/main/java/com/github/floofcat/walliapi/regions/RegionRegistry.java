package com.github.floofcat.walliapi.regions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.World;

public class RegionRegistry {

    private RegionContainer regionContainer;
    private RegionManager regionManager;

    public void init(World world) {
        this.regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();

        this.regionManager = regionContainer.get(BukkitAdapter.adapt(world));
    }

    public ProtectedRegion getRegion(String st) {
        ProtectedRegion region = regionManager.getRegion(st);

        return region;
    }

}
