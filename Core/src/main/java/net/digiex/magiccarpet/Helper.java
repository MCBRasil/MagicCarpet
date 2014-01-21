package net.digiex.magiccarpet;

import java.lang.reflect.InvocationTargetException;

import net.digiex.magiccarpet.nms.api.Abstraction;

import org.bukkit.plugin.Plugin;

/*
 * Magic Carpet 2.4 Copyright (C) 2012-2014 Android, Celtic Minstrel, xzKinGzxBuRnzx
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

public class Helper {

    private static Abstraction nms;

    public Helper(final MagicCarpet plugin) {
        try {
            Helper.nms = init(plugin);
        } catch (final Exception e) {
            Helper.nms = null;
        }
    }

    private Abstraction init(final Plugin plugin) throws ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final String serverPackageName = plugin.getServer().getClass().getPackage().getName();
        final String pluginPackageName = plugin.getClass().getPackage().getName();
        final String version = serverPackageName.substring(serverPackageName.lastIndexOf('.') + 1);
        final Class<?> clazz = Class.forName(pluginPackageName + ".nms." + version + ".Handler");
        if (Abstraction.class.isAssignableFrom(clazz))
            nms = (Abstraction) clazz.getConstructor().newInstance();
        else
            throw new IllegalStateException("Class " + clazz.getName() + " does not implement NMS API");
        return nms;
    }

    public static Abstraction getHandler() {
        return nms;
    }

    public static boolean isEnabled() {
        return nms != null ? true : false;
    }
}
