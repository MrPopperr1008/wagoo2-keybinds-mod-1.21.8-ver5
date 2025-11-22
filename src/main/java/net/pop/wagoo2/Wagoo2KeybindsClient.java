package net.pop.wagoo2;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Wagoo2KeybindsClient implements ClientModInitializer {

    public static KeyBinding pskill;
    public static KeyBinding sskill;

    @Override
    public void onInitializeClient() {
        WagooConfig.load();

        pskill = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wagoo2.pskill",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.wagoo2.keys"
        ));

        sskill = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wagoo2.sskill",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.wagoo2.keys"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (pskill.wasPressed()) {
                if (isValidServer(client)) {
                    client.player.networkHandler.sendChatCommand(WagooConfig.getCommand1());
                }
            }

            if (sskill.wasPressed()) {
                if (isValidServer(client)) {
                    client.player.networkHandler.sendChatCommand(WagooConfig.getCommand2());
                }
            }
        });
    }

    private boolean isValidServer(MinecraftClient client) {
        ServerInfo currentServer = client.getCurrentServerEntry();
        if (currentServer == null) return false;

        return currentServer.address.equalsIgnoreCase(WagooConfig.getTargetIp());
    }
}