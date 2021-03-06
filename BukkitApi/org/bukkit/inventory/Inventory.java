package org.bukkit.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;

/**
 * 各种背包的接口。有关{@link
 * Material#AIR}的行为时是未指定的.
 */
public interface Inventory extends Iterable<ItemStack> {

    /**
     * 返回此背包的大小
     * <p>
     * 原文:Returns the size of the inventory
     *
     * @return 此背包的大小
     */
    public int getSize();

    /**
     * 返回这个背包的最大物品堆叠数量
     * <p>
     * 原文:Returns the maximum stack size for an ItemStack in this inventory.
     *
     * @return 最大物品堆叠数量
     */
    public int getMaxStackSize();

    /**
     * 此方法可以让你改变一个背包的最大物品堆叠数量.
     * <p>
     * <b>警告:</b>
     * <ul>
     * <li>不是所有种类的的背包都遵循本值。
     * <li>若本值大于127，当世界保存和时可能会被缩减。
     * <li>本值不保证被保存；一定要在设置一个格子的最大堆叠数之前修改本值
     * <li>若本值大于这种背包默认的大小，可能不会正确地在客户端上显示
     * </ul>
     * <p>
     * 原文:
     * This method allows you to change the maximum stack size for an
     * inventory.
     * <p>
     * <b>Caveats:</b>
     * <ul>
     * <li>Not all inventories respect this value.
     * <li>Stacks larger than 127 may be clipped when the world is saved.
     * <li>This value is not guaranteed to be preserved; be sure to set it
     *     before every time you want to set a slot over the max stack size.
     * <li>Stacks larger than the default max size for this type of inventory
     *     may not display correctly in the client.
     * </ul>
     *
     * @param size 最大物品堆叠数量
     */
    public void setMaxStackSize(int size);

    /**
     * 返回此背包的名字
     * <p>
     * 原文:Returns the name of the inventory
     *
     * @return 背包名
     */
    public String getName();

    /**
     * 返回在指定索引的物品堆.
     * <p>
     * 原文:Returns the ItemStack found in the slot at the given index
     *
     * @param index 要查找的格子
     * @return 在此格子的物品堆
     */
    public ItemStack getItem(int index);

    /**
     * 在背包指定索引存放物品堆
     * <p>
     * 原文:Stores the ItemStack at the given index of the inventory.
     *
     * @param index 在哪里存放这个物品堆
     * @param item 要设置的物品堆
     */
    public void setItem(int index, ItemStack item);

    /**
     * Stores the given ItemStacks in the inventory. This will try to fill
     * existing stacks and empty slots as well as it can.
     * <p>
     * The returned HashMap contains what it couldn't store, where the key is
     * the index of the parameter, and the value is the ItemStack at that
     * index of the varargs parameter. If all items are stored, it will return
     * an empty HashMap.
     * <p>
     * If you pass in ItemStacks which exceed the maximum stack size for the
     * Material, first they will be added to partial stacks where
     * Material.getMaxStackSize() is not exceeded, up to
     * Material.getMaxStackSize(). When there are no partial stacks left
     * stacks will be split on Inventory.getMaxStackSize() allowing you to
     * exceed the maximum stack size for that material.
     * <p>
     * It is known that in some implementations this method will also set
     * the inputted argument amount to the number of that item not placed in
     * slots.
     *
     * @param items The ItemStacks to add
     * @return A HashMap containing items that didn't fit.
     * @throws IllegalArgumentException if items or any element in it is null
     */
    public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException;

    /**
     * Removes the given ItemStacks from the inventory.
     * <p>
     * It will try to remove 'as much as possible' from the types and amounts
     * you give as arguments.
     * <p>
     * The returned HashMap contains what it couldn't remove, where the key is
     * the index of the parameter, and the value is the ItemStack at that
     * index of the varargs parameter. If all the given ItemStacks are
     * removed, it will return an empty HashMap.
     * <p>
     * It is known that in some implementations this method will also set the
     * inputted argument amount to the number of that item not removed from
     * slots.
     *
     * @param items The ItemStacks to remove
     * @return A HashMap containing items that couldn't be removed.
     * @throws IllegalArgumentException if items is null
     */
    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException;

    /**
     * 返回这个背包的所有物品堆
     * <p>
     * 原文:Returns all ItemStacks from the inventory
     *
     * @return 存储了此背包所有物品的数组
     */
    public ItemStack[] getContents();

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *     be less than or equal to {@link #getSize()}.
     * @throws IllegalArgumentException If the array has more items than the
     *     inventory.
     */
    public void setContents(ItemStack[] items) throws IllegalArgumentException;

    /**
     * Return the contents from the section of the inventory where items can
     * reasonably be expected to be stored. In most cases this will represent
     * the entire inventory, but in some cases it may exclude armor or result
     * slots.
     * <br>
     * It is these contents which will be used for add / contains / remove
     * methods which look for a specific stack.
     *
     * @return inventory storage contents
     */
    public ItemStack[] getStorageContents();

    /**
     * Put the given ItemStacks into the storage slots
     *
     * @param items The ItemStacks to use as storage contents
     * @throws IllegalArgumentException If the array has more items than the
     * inventory.
     */
    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException;

    /**
     * 检测这个背包是否含有指定物品ID的物品堆
     * <p>
     * 原文:Checks if the inventory contains any ItemStacks with the given
     * materialId
     *
     * @param materialId 要检测的物品ID
     * @return 是否含有此物品
     * @deprecated 不安全的参数
     */
    @Deprecated
    public boolean contains(int materialId);

    /**
     * 检测这个背包是否含有指定物品的物品堆
     * <p>
     * 原文:Checks if the inventory contains any ItemStacks with the given
     * material.
     *
     * @param material 要检测的物品
     * @return 是否含有此物品
     * @throws IllegalArgumentException 如果material为null
     */
    public boolean contains(Material material) throws IllegalArgumentException;

    /**
     * 检测这个背包是否含有与给定的物品堆匹配的物品堆
     * <p>
     * 当物品堆的种类和数量都匹配时才返回true
     * <p>
     * 原文:Checks if the inventory contains any ItemStacks matching the given
     * ItemStack.
     * <p>
     * This will only return true if both the type and the amount of the stack
     * match.
     *
     * @param item 要匹配的物品堆
     * @return 如果item为null返回false，如果有完全匹配的物品堆找到返回true
     */
    public boolean contains(ItemStack item);

    /**
     * Checks if the inventory contains any ItemStacks with the given
     * materialId, adding to at least the minimum amount specified.
     *
     * @param materialId The materialId to check for
     * @param amount The minimum amount to look for
     * @return true if this contains any matching ItemStack with the given
     *     materialId and amount
     * @deprecated Magic value
     */
    @Deprecated
    public boolean contains(int materialId, int amount);

    /**
     * Checks if the inventory contains any ItemStacks with the given
     * material, adding to at least the minimum amount specified.
     *
     * @param material The material to check for
     * @param amount The minimum amount
     * @return true if amount is less than 1, true if enough ItemStacks were
     *     found to add to the given amount
     * @throws IllegalArgumentException if material is null
     */
    public boolean contains(Material material, int amount) throws IllegalArgumentException;

    /**
     * Checks if the inventory contains at least the minimum amount specified
     * of exactly matching ItemStacks.
     * <p>
     * An ItemStack only counts if both the type and the amount of the stack
     * match.
     *
     * @param item the ItemStack to match against
     * @param amount how many identical stacks to check for
     * @return false if item is null, true if amount less than 1, true if
     *     amount of exactly matching ItemStacks were found
     * @see #containsAtLeast(ItemStack, int)
     */
    public boolean contains(ItemStack item, int amount);

    /**
     * Checks if the inventory contains ItemStacks matching the given
     * ItemStack whose amounts sum to at least the minimum amount specified.
     *
     * @param item the ItemStack to match against
     * @param amount the minimum amount
     * @return false if item is null, true if amount less than 1, true if
     *     enough ItemStacks were found to add to the given amount
     */
    public boolean containsAtLeast(ItemStack item, int amount);

    /**
     * Returns a HashMap with all slots and ItemStacks in the inventory with
     * given materialId.
     * <p>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given materialId is found, an empty map is returned.
     *
     * @param materialId The materialId to look for
     * @return A HashMap containing the slot index, ItemStack pairs
     * @deprecated Magic value
     */
    @Deprecated
    public HashMap<Integer, ? extends ItemStack> all(int materialId);

    /**
     * Returns a HashMap with all slots and ItemStacks in the inventory with
     * the given Material.
     * <p>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param material The material to look for
     * @return A HashMap containing the slot index, ItemStack pairs
     * @throws IllegalArgumentException if material is null
     */
    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException;

    /**
     * Finds all slots in the inventory containing any ItemStacks with the
     * given ItemStack. This will only match slots if both the type and the
     * amount of the stack match
     * <p>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param item The ItemStack to match against
     * @return A map from slot indexes to item at index
     */
    public HashMap<Integer, ? extends ItemStack> all(ItemStack item);

    /**
     * 查找包含此物品的第一个格子
     * <p>
     * 原文:Finds the first slot in the inventory containing an ItemStack with the
     * given materialId.
     *
     * @param materialId 要查找的物品的ID
     * @return 包含此物品的第一个格子序号
     * @deprecated 不安全的参数
     */
    @Deprecated
    public int first(int materialId);

    /**
     * 查找包含此物品的第一个格子
     * <p>
     * 原文:Finds the first slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material 要查找的物品
     * @return 包含此物品的第一个格子序号
     * @throws IllegalArgumentException 如果material为null
     */
    public int first(Material material) throws IllegalArgumentException;

    /**
     * 查找包含此物品堆的第一个格子堆堆。当物品堆的种类和数量都匹配时才返回true。
     * <p>
     * 原文:Returns the first slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item 要匹配的物品堆
     * @return 给定物品堆所在的格子序号，如果未找到返回-1
     */
    public int first(ItemStack item);

    /**
     * 返回第一个空格子的格子数.
     * <p>
     * 原文:
     * Returns the first empty Slot.
     *
     * @return 第一个空格子的格子数，-1就没有空格子
     */
    public int firstEmpty();

    /**
     * Removes all stacks in the inventory matching the given materialId.
     *
     * @param materialId The material to remove
     * @deprecated Magic value
     */
    @Deprecated
    public void remove(int materialId);

    /**
     * Removes all stacks in the inventory matching the given material.
     *
     * @param material The material to remove
     * @throws IllegalArgumentException if material is null
     */
    public void remove(Material material) throws IllegalArgumentException;

    /**
     * Removes all stacks in the inventory matching the given stack.
     * <p>
     * This will only match a slot if both the type and the amount of the
     * stack match
     *
     * @param item The ItemStack to match against
     */
    public void remove(ItemStack item);

    /**
     * 清理单个格子.
     * <p>
     * 原文:Clears out a particular slot in the index.
     *
     * @param index 格子索引
     */
    public void clear(int index);

    /**
     * 清理整个背包.
     * <p>
     * 原文:Clears out the whole Inventory.
     */
    public void clear();

    /**
     * Gets a list of players viewing the inventory. Note that a player is
     * considered to be viewing their own inventory and internal crafting
     * screen even when said inventory is not open. They will normally be
     * considered to be viewing their inventory even when they have a
     * different inventory screen open, but it's possible for customized
     * inventory screens to exclude the viewer's inventory, so this should
     * never be assumed to be non-empty.
     *
     * @return A list of HumanEntities who are viewing this Inventory.
     */
    public List<HumanEntity> getViewers();

    /**
     * 返回此背包的标题
     * <p>
     * 原文:Returns the title of this inventory.
     *
     * @return 背包的标题
     */
    public String getTitle();

    /**
     * 返回这个背包的种类
     * <p>
     * 原文:Returns what type of inventory this is.
     *
     * @return 背包的种类
     */
    public InventoryType getType();

    /**
     * Gets the block or entity belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    public InventoryHolder getHolder();

    @Override
    public ListIterator<ItemStack> iterator();

    /**
     * Returns an iterator starting at the given index. If the index is
     * positive, then the first call to next() will return the item at that
     * index; if it is negative, the first call to previous will return the
     * item at index (getSize() + index).
     *
     * @param index The index.
     * @return An iterator.
     */
    public ListIterator<ItemStack> iterator(int index);

    /**
     * Get the location of the block or entity which corresponds to this inventory. May return null if this container
     * was custom created or is a virtual / subcontainer.
     *
     * @return location or null if not applicable.
     */
    public Location getLocation();
}
