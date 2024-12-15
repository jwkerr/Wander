package au.lupine.wander.listener;

import au.lupine.wander.manager.RecipeManager;
import au.lupine.wander.object.recipe.TradeSchema;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class WanderingTraderListener implements Listener {

    @EventHandler
    public void onWanderingTraderSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof WanderingTrader trader)) return;

        TradeSchema schema = RecipeManager.getInstance().getSchema();

        List<MerchantRecipe> recipes = new ArrayList<>(schema.generate());
        recipes.addAll(trader.getRecipes());

        trader.setRecipes(recipes);
    }
}