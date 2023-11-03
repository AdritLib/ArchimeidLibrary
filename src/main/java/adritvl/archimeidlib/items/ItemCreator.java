package adritvl.archimeidlib.items;

import adritvl.archimeidlib.utils.TextFormats;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCreator {
    private final ItemStack item;
    private final ItemMeta meta;
    private List<String> lore;
    public ItemCreator(Material material){
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
        this.lore = new ArrayList<>();
    }
    public ItemCreator(ItemStack item){
        this.item = item;
        this.meta = item.getItemMeta();
        if(meta != null && meta.getLore() != null){
            this.lore = item.getItemMeta().getLore();
        }else{
            this.lore = new ArrayList<>();
        }
    }

    /**Set a display name from item
     * @param name name is the displayed name of item*/
    public void setDisplayName(String name){
        this.meta.setDisplayName(TextFormats.color(name));
    }

    /**Return the displayed name from item*/
    public String getDisplayName(){return this.meta.getDisplayName();}

    /**Add a line a new lore from this class
     * @param text text is the line to add*/
    public void addLine(String text){
        lore.add(TextFormats.color(text));
    }

    /**Set Lore for Lore from this class
     * @param lore the new lore to replace lore*/
    public void setLore(List<String> lore){
        List<String> lore1 = new ArrayList<>();
        for(String s : lore){
            lore1.add(TextFormats.color(s));
        }
        this.lore = lore1;
    }

    /**Add lore to lore from this class
     * @param lore the lore to add*/
    public void addAllLore(List<String> lore){
        List<String> lore1 = new ArrayList<>();
        for(String s : lore){
            lore1.add(TextFormats.color(s));
        }
        this.lore.addAll(lore1);
    }

    /**Get index from lore from this class, if this can't get it throws -1
     * @param line text to check if it contains it*/
    public int getLine(String line){
        for(int i=0;i<lore.size();i++){
            if(lore.get(i).contains(line)) return i;
        }
        return -1;
    }
    public ItemStack getItem(){
        return item;
    }

    /**Set meta to item from this class and apply lore (Only isn't empty)*/
    public void updateMeta(){
        if(!lore.isEmpty()) {
            this.meta.setLore(lore);
        }
        this.item.setItemMeta(meta);
    }

    /**Set custom model for this item
     * @param model number of model data*/
    public void setModelData(int model){
        meta.setCustomModelData(model);
    }

    /**Add a custom attribute*/
    public void addAttribute(Attribute attribute, String operation, double value, String slot){
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(),"attribute."+attribute.name(), value, getAttributeOperation(operation), getSlot(slot));
        meta.addAttributeModifier(attribute, mod);
    }
    private static Attribute getAttribute(String name){
        switch (name){
            case "attack_damage":
                return Attribute.GENERIC_ATTACK_DAMAGE;
            case "armor":
                return Attribute.GENERIC_ARMOR;
            case "attack_speed":
                return Attribute.GENERIC_ATTACK_SPEED;
            case "max_health":
                return Attribute.GENERIC_MAX_HEALTH;
            case "speed_move":
                return Attribute.GENERIC_MOVEMENT_SPEED;
            default:
                return null;
        }
    }
    private static AttributeModifier.Operation getAttributeOperation(String name){
        switch (name){
            case "scalar":
                return AttributeModifier.Operation.ADD_SCALAR;
            case "number":
                return AttributeModifier.Operation.ADD_NUMBER;
            case "multiply":
                return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
            default:
                return null;
        }
    }
    private static EquipmentSlot getSlot(String name){
        switch (name){
            case "HEAD":
                return EquipmentSlot.HEAD;
            case "CHEST":
                return EquipmentSlot.CHEST;
            case "LEGS":
                return EquipmentSlot.LEGS;
            case "FEET":
                return EquipmentSlot.FEET;
            case "OFF_HAND":
                return EquipmentSlot.OFF_HAND;
            case "HAND":
                return EquipmentSlot.HAND;
            default:
                return null;
        }
    }

    /**Return ID from persistent data of the meta*/
    public String getID(){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(!data.has(KeysValues.ID, PersistentDataType.STRING)) return null;
        return data.get(KeysValues.ID, PersistentDataType.STRING);
    }

    /**Set ID of the item in persistent data*/
    public void setID(String ID){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(KeysValues.ID, PersistentDataType.STRING, ID);
    }

    public void setInteract(boolean value){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        int valueInt = 0;
        if(value) valueInt = 1;
        data.set(KeysValues.INTERACT, PersistentDataType.INTEGER, valueInt);
    }

    /**If value is TRUE can interact with the world else if is FALSE can't do it*/
    public boolean canInteract(){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        try{
            Object value = data.get(KeysValues.INTERACT, PersistentDataType.INTEGER);
            if(value == null) return true;
            int valueInt = (int) value;

            return valueInt == 1;
        }catch(Exception ex){
            return true;
        }
    }

    /**Set internal name from this item in persistent data
     * @param name internal name for this meta*/
    public void setName(String name){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(KeysValues.NAME, PersistentDataType.STRING, name);
    }

    /**Return the internal name from meta*/
    public String getName(){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(KeysValues.NAME, PersistentDataType.STRING);
    }

    /**Set color armor for this item (Only works with LEATHER armor pieces)
     * @param red amount of red (0-255)
     * @param green amount of green (0-255)
     * @param blue amount of blue (0-255)*/
    public void setColor(int red, int green, int blue) {
        try {
            if(item != null && meta instanceof  LeatherArmorMeta) {
                LeatherArmorMeta armors = (LeatherArmorMeta) meta;
                armors.setColor(Color.fromRGB(red,green,blue));
                item.setItemMeta(armors);
            }
        }catch (IllegalArgumentException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage("Error: color a item with ItemCreator.");
        }
    }
}
