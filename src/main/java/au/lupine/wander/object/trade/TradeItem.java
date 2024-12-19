package au.lupine.wander.object.trade;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import au.lupine.wander.object.number.VariableInteger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeItem {

    private final Material material;
    private final VariableInteger amount;
    private final Component name;
    private final List<Component> lore = new ArrayList<>();
    private final Map<Enchantment, VariableInteger> enchantments = new HashMap<>();
    private final boolean unbreakable;

    public TradeItem(JsonObject jsonObject) {
        material = Material.valueOf(jsonObject.get("material").getAsString());
        if (!material.isItem()) throw new RuntimeException("Specified material " + material.name() + " is not an item");

        VariableInteger amount;
        try {
            amount = VariableInteger.fromJSONElement(jsonObject.get("amount"));
        } catch (Exception e) {
            amount = new VariableInteger(1, 1);
        }
        this.amount = amount;

        MiniMessage mm = MiniMessage.miniMessage();
        if (jsonObject.has("name")) {
            name = mm.deserialize(jsonObject.get("name").getAsString());
        } else {
            name = null;
        }

        if (jsonObject.has("lore")) {
            for (JsonElement element : jsonObject.get("lore").getAsJsonArray()) {
                lore.add(mm.deserialize(element.getAsString()));
            }
        }

        if (jsonObject.has("enchantments")) {
            for (JsonElement element : jsonObject.get("enchantments").getAsJsonArray()) {
                JsonObject enchantObject = element.getAsJsonObject();

                Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(enchantObject.get("enchantment").getAsString()));
                VariableInteger level = VariableInteger.fromJSONElement(enchantObject.get("level"));

                enchantments.put(enchantment, level);
            }
        }

        if (jsonObject.has("unbreakable")) {
            unbreakable = jsonObject.get("unbreakable").getAsBoolean();
        } else {
            unbreakable = false;
        }
    }

    public ItemStack generate() {
        ItemStack item = new ItemStack(material, amount.generate());

        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.displayName(name);
        if (!lore.isEmpty()) meta.lore(lore);

        for (Map.Entry<Enchantment, VariableInteger> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue().generate(), true);
        }

        meta.setUnbreakable(unbreakable);

        item.setItemMeta(meta);

        return item;
    }
}
