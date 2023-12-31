package net.isaiah.deathstatues.screen;

import net.isaiah.deathstatues.block.entity.DeathStatueBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class DeathStatuesScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final DeathStatueBlockEntity blockEntity;

    public DeathStatuesScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public DeathStatuesScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.DEATH_STATUE_SCREEN_HANDLER, syncId);
        DeathStatuesScreenHandler.checkSize((Inventory) blockEntity, 28);
       //DeathStatuesScreenHandler.checkSize(inventory, 27);
        this.inventory = (Inventory) blockEntity;
        //this.inventory = inventory;
        this.blockEntity = (DeathStatueBlockEntity) blockEntity;

        //addStatueInventory(inventory);
        addStatueInventory(((Inventory) blockEntity));
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.inventory.getStack(27).getName().getString().equals("Death Statue Entity")) {
            System.out.println("Base has default statue item");
            this.setStackInSlot(27, 1, new ItemStack(Items.AIR));
        }
        return super.onButtonClick(player, id);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        //System.out.println("player slot index:" + slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            //System.out.println("slot index:" + slot);
            if (slot < this.inventory.size() ? this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true) : !this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addStatueInventory(Inventory inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory,l + i * 9, 8 + l * 18, 18 + i * 18));
            }
        }
        this.addSlot(new Slot(inventory,27, -13, 36));
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i,8 + i * 18, 142));
        }
    }
}
