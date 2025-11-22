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

    public static KeyBinding keybind1;
    public static KeyBinding keybind2;

    @Override
    public void onInitializeClient() {
        WagooConfig.load();

        keybind1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wagoo2.keybind1",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.wagoo2.keys"
        ));

        keybind2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wagoo2.keybind2",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.wagoo2.keys"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (keybind1.wasPressed()) {
                if (isValidServer(client)) {
                    client.player.networkHandler.sendChatCommand(WagooConfig.getCommand1());
                }
            }

            if (keybind2.wasPressed()) {
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