package kr.toxicity.library.dynamicuv.test;

import kr.toxicity.library.dynamicuv.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public final class TestPlugin extends JavaPlugin {

    private static final float DIV_FACTOR = 16 / 0.9375F;

    private static final UVNamespace UV_NAMESPACE = new UVNamespace(
            "uv_test",
            "player_limb"
    );

    private static final UVModel HEAD = new UVModel(
            UV_NAMESPACE,
            "head"
    ).addElement(new UVElement(
            new ElementVector(8F, 8F, 8F).div(DIV_FACTOR),
            new ElementVector(0, 4F, 0).div(DIV_FACTOR),
            new UVSpace(8, 8, 8),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(8, 8)),
                    Map.entry(UVFace.SOUTH, new UVPos(24, 8)),
                    Map.entry(UVFace.EAST, new UVPos(0, 8)),
                    Map.entry(UVFace.WEST, new UVPos(16, 8)),
                    Map.entry(UVFace.UP, new UVPos(8, 0)),
                    Map.entry(UVFace.DOWN, new UVPos(16, 0))
            )
    )).addElement(new UVElement(
            new ElementVector(8F, 8F, 8F).div(DIV_FACTOR).inflate(0.5F),
            new ElementVector(0, 4F, 0).div(DIV_FACTOR),
            new UVSpace(8, 8, 8),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(8 + 32, 8)),
                    Map.entry(UVFace.SOUTH, new UVPos(24 + 32, 8)),
                    Map.entry(UVFace.EAST, new UVPos(32, 8)),
                    Map.entry(UVFace.WEST, new UVPos(16 + 32, 8)),
                    Map.entry(UVFace.UP, new UVPos(8 + 32, 0)),
                    Map.entry(UVFace.DOWN, new UVPos(16 + 32, 0))
            )
    ));
    private static final UVModel CHEST = new UVModel(
            UV_NAMESPACE,
            "chest"
    ).addElement(new UVElement(
            new ElementVector(8, 4, 4).div(DIV_FACTOR),
            new ElementVector(0, 2, 0).div(DIV_FACTOR),
            new UVSpace(8, 4, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 20)),
                    Map.entry(UVFace.SOUTH, new UVPos(32, 20)),
                    Map.entry(UVFace.EAST, new UVPos(16, 20)),
                    Map.entry(UVFace.WEST, new UVPos(28, 20)),
                    Map.entry(UVFace.UP, new UVPos(20, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(8, 4, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(0, 2, 0).div(DIV_FACTOR),
            new UVSpace(8, 4, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 20 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(32, 20 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(16, 20 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(28, 20 + 16)),
                    Map.entry(UVFace.UP, new UVPos(20, 16 + 16))
            )
    ));
    private static final UVModel WAIST = new UVModel(
            UV_NAMESPACE,
            "waist"
    ).addElement(new UVElement(
            new ElementVector(8, 4, 4).div(DIV_FACTOR),
            new ElementVector(0, 2, 0).div(DIV_FACTOR),
            new UVSpace(8, 4, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 24)),
                    Map.entry(UVFace.SOUTH, new UVPos(32, 24)),
                    Map.entry(UVFace.EAST, new UVPos(16, 24)),
                    Map.entry(UVFace.WEST, new UVPos(28, 24))
            )
    )).addElement(new UVElement(
            new ElementVector(8, 4, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(0, 2, 0).div(DIV_FACTOR),
            new UVSpace(8, 4, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 24 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(32, 24 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(16, 24 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(28, 24 + 16))
            )
    ));
    private static final UVModel HIP = new UVModel(
            UV_NAMESPACE,
            "hip"
    ).addElement(new UVElement(
            new ElementVector(8, 4, 4).div(DIV_FACTOR),
            new ElementVector(0, 2, 0).div(DIV_FACTOR),
            new UVSpace(8, 4, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 28)),
                    Map.entry(UVFace.SOUTH, new UVPos(32, 28)),
                    Map.entry(UVFace.EAST, new UVPos(16, 28)),
                    Map.entry(UVFace.WEST, new UVPos(28, 28)),
                    Map.entry(UVFace.DOWN, new UVPos(28, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(8, 4, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(0, 2, 0).div(DIV_FACTOR),
            new UVSpace(8, 4, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 28 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(32, 28 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(16, 28 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(28, 28 + 16)),
                    Map.entry(UVFace.DOWN, new UVPos(28, 16 + 16))
            )
    ));
    private static final UVModel LEFT_LEG = new UVModel(
            UV_NAMESPACE,
            "left_leg"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 52)),
                    Map.entry(UVFace.SOUTH, new UVPos(28, 52)),
                    Map.entry(UVFace.EAST, new UVPos(16, 52)),
                    Map.entry(UVFace.WEST, new UVPos(24, 52)),
                    Map.entry(UVFace.UP, new UVPos(20, 48))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20 - 16, 52)),
                    Map.entry(UVFace.SOUTH, new UVPos(28 - 16, 52)),
                    Map.entry(UVFace.EAST, new UVPos(0, 52)),
                    Map.entry(UVFace.WEST, new UVPos(24 - 16, 52)),
                    Map.entry(UVFace.UP, new UVPos(20 - 16, 48))
            )
    ));
    private static final UVModel LEFT_FORELEG = new UVModel(
            UV_NAMESPACE,
            "left_foreleg"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20, 58)),
                    Map.entry(UVFace.SOUTH, new UVPos(28, 58)),
                    Map.entry(UVFace.EAST, new UVPos(16, 58)),
                    Map.entry(UVFace.WEST, new UVPos(24, 58)),
                    Map.entry(UVFace.DOWN, new UVPos(24, 48))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(20 - 16, 58)),
                    Map.entry(UVFace.SOUTH, new UVPos(28 - 16, 58)),
                    Map.entry(UVFace.EAST, new UVPos(0, 58)),
                    Map.entry(UVFace.WEST, new UVPos(24 - 16, 58)),
                    Map.entry(UVFace.DOWN, new UVPos(24 - 16, 48))
            )
    ));
    private static final UVModel RIGHT_LEG = new UVModel(
            UV_NAMESPACE,
            "right_leg"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(4, 20)),
                    Map.entry(UVFace.SOUTH, new UVPos(12, 20)),
                    Map.entry(UVFace.EAST, new UVPos(0, 20)),
                    Map.entry(UVFace.WEST, new UVPos(8, 20)),
                    Map.entry(UVFace.UP, new UVPos(4, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(4, 20 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(12, 20 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(0, 20 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(8, 20 + 16)),
                    Map.entry(UVFace.UP, new UVPos(4, 16 + 16))
            )
    ));
    private static final UVModel RIGHT_FORELEG = new UVModel(
            UV_NAMESPACE,
            "right_foreleg"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(4, 26)),
                    Map.entry(UVFace.SOUTH, new UVPos(12, 26)),
                    Map.entry(UVFace.EAST, new UVPos(0, 26)),
                    Map.entry(UVFace.WEST, new UVPos(8, 26)),
                    Map.entry(UVFace.DOWN, new UVPos(8, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(4, 26 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(12, 26 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(0, 26 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(8, 26 + 16)),
                    Map.entry(UVFace.DOWN, new UVPos(8, 16 + 16))
            )
    ));
    private static final UVModel LEFT_ARM = new UVModel(
            UV_NAMESPACE,
            "left_arm"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36, 52)),
                    Map.entry(UVFace.SOUTH, new UVPos(44, 52)),
                    Map.entry(UVFace.EAST, new UVPos(32, 52)),
                    Map.entry(UVFace.WEST, new UVPos(40, 52)),
                    Map.entry(UVFace.UP, new UVPos(36, 48))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36 + 16, 52)),
                    Map.entry(UVFace.SOUTH, new UVPos(44 + 16, 52)),
                    Map.entry(UVFace.EAST, new UVPos(32 + 16, 52)),
                    Map.entry(UVFace.WEST, new UVPos(40 + 16, 52)),
                    Map.entry(UVFace.UP, new UVPos(36 + 16, 48))
            )
    ));
    private static final UVModel LEFT_FOREARM = new UVModel(
            UV_NAMESPACE,
            "left_forearm"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36, 58)),
                    Map.entry(UVFace.SOUTH, new UVPos(44, 58)),
                    Map.entry(UVFace.EAST, new UVPos(32, 58)),
                    Map.entry(UVFace.WEST, new UVPos(40, 58)),
                    Map.entry(UVFace.DOWN, new UVPos(40, 48))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(-2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36 + 16, 58)),
                    Map.entry(UVFace.SOUTH, new UVPos(44 + 16, 58)),
                    Map.entry(UVFace.EAST, new UVPos(32 + 16, 58)),
                    Map.entry(UVFace.WEST, new UVPos(40 + 16, 58)),
                    Map.entry(UVFace.DOWN, new UVPos(40 + 16, 48))
            )
    ));
    private static final UVModel RIGHT_ARM = new UVModel(
            UV_NAMESPACE,
            "right_arm"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 20)),
                    Map.entry(UVFace.SOUTH, new UVPos(52, 20)),
                    Map.entry(UVFace.EAST, new UVPos(40, 20)),
                    Map.entry(UVFace.WEST, new UVPos(48, 20)),
                    Map.entry(UVFace.UP, new UVPos(44, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 20 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(52, 20 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(40, 20 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(48, 20 + 16)),
                    Map.entry(UVFace.UP, new UVPos(44, 16 + 16))
            )
    ));
    private static final UVModel RIGHT_FOREARM = new UVModel(
            UV_NAMESPACE,
            "right_forearm"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 24)),
                    Map.entry(UVFace.SOUTH, new UVPos(52, 24)),
                    Map.entry(UVFace.EAST, new UVPos(40, 24)),
                    Map.entry(UVFace.WEST, new UVPos(48, 24)),
                    Map.entry(UVFace.DOWN, new UVPos(48, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(2, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 24 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(52, 24 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(40, 24 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(48, 24 + 16)),
                    Map.entry(UVFace.DOWN, new UVPos(48, 16 + 16))
            )
    ));
    private static final UVModel SLIM_LEFT_ARM = new UVModel(
            UV_NAMESPACE,
            "left_slim_arm"
    ).addElement(new UVElement(
            new ElementVector(3, 6, 4).div(DIV_FACTOR),
            new ElementVector(-1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(3, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36, 52)),
                    Map.entry(UVFace.SOUTH, new UVPos(43, 52)),
                    Map.entry(UVFace.EAST, new UVPos(32, 52)),
                    Map.entry(UVFace.WEST, new UVPos(39, 52)),
                    Map.entry(UVFace.UP, new UVPos(36, 48))
            )
    )).addElement(new UVElement(
            new ElementVector(3, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(-1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(3, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36 + 16, 52)),
                    Map.entry(UVFace.SOUTH, new UVPos(43 + 16, 52)),
                    Map.entry(UVFace.EAST, new UVPos(32 + 16, 52)),
                    Map.entry(UVFace.WEST, new UVPos(39 + 16, 52)),
                    Map.entry(UVFace.UP, new UVPos(36 + 16, 48))
            )
    ));
    private static final UVModel SLIM_LEFT_FOREARM = new UVModel(
            UV_NAMESPACE,
            "left_slim_forearm"
    ).addElement(new UVElement(
            new ElementVector(3, 6, 4).div(DIV_FACTOR),
            new ElementVector(-1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(3, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36, 58)),
                    Map.entry(UVFace.SOUTH, new UVPos(43, 58)),
                    Map.entry(UVFace.EAST, new UVPos(32, 58)),
                    Map.entry(UVFace.WEST, new UVPos(39, 58)),
                    Map.entry(UVFace.DOWN, new UVPos(39, 48))
            )
    )).addElement(new UVElement(
            new ElementVector(3, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(-1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(3, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(36 + 16, 58)),
                    Map.entry(UVFace.SOUTH, new UVPos(43 + 16, 58)),
                    Map.entry(UVFace.EAST, new UVPos(32 + 16, 58)),
                    Map.entry(UVFace.WEST, new UVPos(39 + 16, 58)),
                    Map.entry(UVFace.DOWN, new UVPos(39 + 16, 48))
            )
    ));
    private static final UVModel SLIM_RIGHT_ARM = new UVModel(
            UV_NAMESPACE,
            "right_slim_arm"
    ).addElement(new UVElement(
            new ElementVector(3, 6, 4).div(DIV_FACTOR),
            new ElementVector(1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(3, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 20)),
                    Map.entry(UVFace.SOUTH, new UVPos(51, 20)),
                    Map.entry(UVFace.EAST, new UVPos(40, 20)),
                    Map.entry(UVFace.WEST, new UVPos(47, 20)),
                    Map.entry(UVFace.UP, new UVPos(44, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(3, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(3, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 20 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(51, 20 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(40, 20 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(47, 20 + 16)),
                    Map.entry(UVFace.UP, new UVPos(44, 16 + 16))
            )
    ));
    private static final UVModel SLIM_RIGHT_FOREARM = new UVModel(
            UV_NAMESPACE,
            "right_slim_forearm"
    ).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR),
            new ElementVector(1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.RGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 24)),
                    Map.entry(UVFace.SOUTH, new UVPos(51, 24)),
                    Map.entry(UVFace.EAST, new UVPos(40, 24)),
                    Map.entry(UVFace.WEST, new UVPos(47, 24)),
                    Map.entry(UVFace.DOWN, new UVPos(47, 16))
            )
    )).addElement(new UVElement(
            new ElementVector(4, 6, 4).div(DIV_FACTOR).inflate(0.25F),
            new ElementVector(1.5F, -3, 0).div(DIV_FACTOR),
            new UVSpace(4, 6, 4),
            UVElement.ColorType.ARGB,
            Map.ofEntries(
                    Map.entry(UVFace.NORTH, new UVPos(44, 24 + 16)),
                    Map.entry(UVFace.SOUTH, new UVPos(51, 24 + 16)),
                    Map.entry(UVFace.EAST, new UVPos(40, 24 + 16)),
                    Map.entry(UVFace.WEST, new UVPos(47, 24 + 16)),
                    Map.entry(UVFace.DOWN, new UVPos(47, 16 + 16))
            )
    ));

    private final List<org.bukkit.inventory.ItemStack> modelData = new ArrayList<>();

    private void addData(@NotNull UVModel model, @NotNull BufferedImage image) {
        var data = model.write(image);
        var item = new ItemStack(Items.DIAMOND);
        item.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(
                Collections.emptyList(),
                data.flags(),
                Collections.emptyList(),
                data.colors()
        ));
        item.set(DataComponents.ITEM_MODEL, ResourceLocation.parse(model.itemModelNamespace()));
        modelData.add(CraftItemStack.asBukkitCopy(item));
    }

    private void writeModel(@NotNull UVModel model, @NotNull File dir) {
        model.asJson("one_pixel")
                .parallelStream()
                .forEach(builder -> writeAs(builder, dir));
    }

    private void writeAs(@NotNull UVByteBuilder builder, @NotNull File dir) {
        var file = new File(dir, builder.path());
        var parent = file.getParentFile();
        parent.mkdirs();
        try(
                var output = new FileOutputStream(file);
                var buffered = new BufferedOutputStream(output)
        ) {
            buffered.write(builder.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled.");
        var dir = getDataPath()
                .resolve("build")
                .toFile();
        writeModel(HEAD, dir);
        writeModel(CHEST, dir);
        writeModel(WAIST, dir);
        writeModel(HIP, dir);
        writeModel(LEFT_LEG, dir);
        writeModel(LEFT_FORELEG, dir);
        writeModel(RIGHT_LEG, dir);
        writeModel(RIGHT_FORELEG, dir);
        writeModel(LEFT_ARM, dir);
        writeModel(LEFT_FOREARM, dir);
        writeModel(RIGHT_ARM, dir);
        writeModel(RIGHT_FOREARM, dir);
        writeAs(UVByteBuilder.emptyImage(UV_NAMESPACE, "one_pixel"), dir);
        try (
                var stream = Objects.requireNonNull(getResource("test_skin.png"));
                var buffered = new BufferedInputStream(stream)
        ) {
            var image = ImageIO.read(buffered);
            addData(HEAD, image);
            addData(CHEST, image);
            addData(WAIST, image);
            addData(HIP, image);
            addData(LEFT_LEG, image);
            addData(LEFT_FORELEG, image);
            addData(RIGHT_LEG, image);
            addData(RIGHT_FORELEG, image);
            addData(LEFT_ARM, image);
            addData(LEFT_FOREARM, image);
            addData(RIGHT_ARM, image);
            addData(RIGHT_FOREARM, image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void join(@NotNull PlayerJoinEvent event) {
                for (org.bukkit.inventory.ItemStack modelDatum : modelData) {
                    event.getPlayer().getInventory().addItem(modelDatum);
                }
            }
        }, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }
}
