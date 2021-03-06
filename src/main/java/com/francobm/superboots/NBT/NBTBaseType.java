package com.francobm.superboots.NBT;

import com.francobm.superboots.utils.UtilsSB;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;

public enum NBTBaseType {

    BYTE(byte.class, "Byte"),
    BYTE_ARRAY(byte[].class, "ByteArray"),
    DOUBLE(double.class, "Double"),
    FLOAT(float.class, "Float"),
    INT(int.class, "Int"),
    INT_ARRAY(int[].class, "IntArray"),
    LONG(long.class, "Long"),
    SHORT(short.class, "Short"),
    STRING(String.class, "String");

    private Class<?> innerClazz;
    private Class<?> nbtBaseClass;
    private String name;

    <T> NBTBaseType(Class<T> innerClazz, String name){
        try{
            this.innerClazz = innerClazz;
            String version = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            if(!UtilsSB.getOldNMS()){
                this.nbtBaseClass = Class.forName("net.minecraft.nbt.NBTBase");
            }
            else{
                this.nbtBaseClass = Class.forName(version + ".NBTTag" + name);
            }
            this.name = name;
        }
        catch(Exception ex){ex.printStackTrace();}
    }

    public static NBTBaseType get(Class<?> clazz){
        for(NBTBaseType type : values()){
            if(type.innerClazz.equals(clazz)){
                return type;
            }
        }

        if(clazz == Float.class){return FLOAT;}
        else if(clazz == Integer.class){return INT;}
        return null;
    }

    public String getName(){
        return this.name;
    }

    public <T> Object make(T value){
        try{
            Constructor<?> m = nbtBaseClass.getConstructor(innerClazz);
            m.setAccessible(true);
            Object o = m.newInstance(value);
            m.setAccessible(false);

            return o;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static NBTBaseType getByClass(Class<?> clazz){
        for(NBTBaseType t : values()){
            if(t.innerClazz.equals(clazz)){return t;}
        }
        return null;
    }

    public static NBTBaseType getByNBTBaseClass(Class<?> clazz){
        for(NBTBaseType t : values()){
            if(t.nbtBaseClass.equals(clazz)){return t;}
        }
        return null;
    }

    public static NBTBaseType getFromObject(Object o){
        NBTBaseType t = getByClass(o.getClass());
        if(t != null){return t;}

        try{
            return getByClass((Class<?>) o.getClass().getField("TYPE").get(null));
        }
        catch(Exception ignore){}
        return null;
    }
}
