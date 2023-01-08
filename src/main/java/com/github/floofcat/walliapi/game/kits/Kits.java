package com.github.floofcat.walliapi.game.kits;

import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Kits {

    private Set<ItemStack> kitItems = new HashSet<>();

    public void setKit(Set<ItemStack> itemStack) {
        this.kitItems = itemStack;
    }

    public Set<ItemStack> getKit() {
        return this.kitItems;
    }
}
