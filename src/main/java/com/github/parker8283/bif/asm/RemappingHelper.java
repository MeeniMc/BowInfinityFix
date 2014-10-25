package com.github.parker8283.bif.asm;

import java.util.HashMap;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

/**
 * Stolen directly from AppleCore
 *
 * @author squeek502
 */
public class RemappingHelper
{
    // initialized in Parker8283Core.injectData
    public static boolean obfuscated = false;
    private static HashMap<String, String> classNameToObfClassNameCache = new HashMap<String, String>();
    private static HashMap<String, String> obfClassNameToClassNameCache = new HashMap<String, String>();

    private static void cacheObfClassMapping(String obfClassName, String className)
    {
        obfClassNameToClassNameCache.put(obfClassName, className);
        classNameToObfClassNameCache.put(className, obfClassName);
    }

    public static String toDeobfClassName(String obfClassName)
    {
        if (obfuscated)
        {
            if (!obfClassNameToClassNameCache.containsKey(obfClassName))
                cacheObfClassMapping(obfClassName, FMLDeobfuscatingRemapper.INSTANCE.map(obfClassName.replace('.', '/')).replace('/', '.'));

            return obfClassNameToClassNameCache.get(obfClassName);
        }
        else
            return obfClassName;
    }

    public static String toObfClassName(String deobfClassName)
    {
        if (obfuscated)
        {
            if (!classNameToObfClassNameCache.containsKey(deobfClassName))
                cacheObfClassMapping(FMLDeobfuscatingRemapper.INSTANCE.unmap(deobfClassName.replace('.', '/')).replace('/', '.'), deobfClassName);

            return classNameToObfClassNameCache.get(deobfClassName);
        }
        else
            return deobfClassName;
    }

    public static String getInternalClassName(String className)
    {
        return toObfClassName(className).replace('.', '/');
    }

    public static String getDescriptor(String className)
    {
        return "L" + getInternalClassName(className) + ";";
    }
}
