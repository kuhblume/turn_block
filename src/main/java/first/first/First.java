package first.first;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class First extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // プラグイン有効化時（初期化とか書く）
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // プラグイン無効化時
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 手持ちのアイテムを取得
        ItemStack i = event.getPlayer().getItemInHand();
        // クリックしたブロックを取得
        Block b = event.getClickedBlock();

        //メインハンドのみ動作
        if (!(event.getHand() == EquipmentSlot.HAND)) return;
        // 手持ちのアイテムは木の剣か
        if (!(i.getType() == Material.WOODEN_SWORD)) return;

        // ブロックに対するクリック（右か左かで分岐）
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            // 対象ブロックに向きがあるか
            if (!(b.getBlockData() instanceof org.bukkit.block.data.Directional)) return;
            //BlockDataをDirectionalで扱う
            Directional blockDirectional = (Directional) b.getBlockData();
            //ブロックの向きを取得
            BlockFace face = blockDirectional.getFacing();
            //同ブロックの向きを変更
            blockDirectional.setFacing(turnHorizontal(face));
            //変更を反映
            b.setBlockData(blockDirectional);
        }else if((event.getAction() == Action.LEFT_CLICK_BLOCK)){
            // 対象ブロックに向きがあるか
            if (!(b.getBlockData() instanceof org.bukkit.block.data.Directional)) return;
            //さらに上下方向を持つか
            if (!(((Directional) b.getBlockData()).getFaces().size()==6 )) return;

            Directional blockDirectional = (Directional) b.getBlockData();
            BlockFace face = blockDirectional.getFacing();
            blockDirectional.setFacing(turnVertical(face));
            b.setBlockData(blockDirectional);
        }
    }


    private BlockFace turnHorizontal(BlockFace blockFace) {
        switch (blockFace) {
            case NORTH:
                return BlockFace.EAST;
            case EAST:
                return BlockFace.SOUTH;
            case SOUTH:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.NORTH;
            default:
                return BlockFace.NORTH;
        }
    }

    private BlockFace turnVertical(BlockFace blockFace) {
        switch (blockFace) {
            case UP:
                return BlockFace.DOWN;
            case DOWN:
                return BlockFace.UP;
            default:
                return BlockFace.UP;
        }
    }

}

