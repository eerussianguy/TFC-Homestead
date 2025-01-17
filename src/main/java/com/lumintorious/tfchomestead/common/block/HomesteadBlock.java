package com.lumintorious.tfchomestead.common.block;

import com.lumintorious.tfchomestead.TFCHomestead;
import com.lumintorious.tfchomestead.common.block.entity.FoodHolderBlockEntity;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class HomesteadBlock extends DeviceBlock {
    public HomesteadBlock(ExtendedProperties properties) {
        super(properties, InventoryRemoveBehavior.DROP);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof FoodHolderBlockEntity foodHolder) {
            boolean result = foodHolder.onRightClick(pPlayer);
            return result ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof FoodHolderBlockEntity foodHolder) {
            boolean result = foodHolder.onLeftClick(pPlayer);
        }
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        List<ItemStack> stacks = new LinkedList<>();
        if(pBuilder.getParameter(LootContextParams.BLOCK_ENTITY) instanceof FoodHolderBlockEntity entity) {
            entity.getDrops(stacks);
        }
        return stacks;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof FoodHolderBlockEntity foodHolder) {
            foodHolder.updatePreservation();
        }
    }


}
