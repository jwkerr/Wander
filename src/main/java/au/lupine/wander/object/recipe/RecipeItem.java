package au.lupine.wander.object.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import au.lupine.wander.object.number.VariableInteger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RecipeItem {

    private final Material material;
    private final VariableInteger amount;
    private final Component name;
    private final List<Component> lore = new ArrayList<>();

    public RecipeItem(JsonObject jsonObject) {
        material = Material.valueOf(jsonObject.get("material").getAsString());

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
    }

    public ItemStack generate() {
        ItemStack item = new ItemStack(material, amount.generate());

        ItemMeta meta = item.getItemMeta();
        if (name != null) meta.displayName(name);
        if (!lore.isEmpty()) meta.lore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
