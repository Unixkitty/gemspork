package com.unixkitty.gemspork;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Gemspork.MODID)
public class Gemspork
{
    // The MODID value here should match an entry in the META-INF/mods.toml file
    public static final String MODID = "gemspork";
    public static final String MODNAME = "Gemspork (library)";

    private static final Logger LOG = LogManager.getLogger(MODNAME);

    public Gemspork()
    {
        log().info(MODNAME + " is loading");
    }

    public static Logger log()
    {
        return LOG;
    }
}
