package net.pop.wagoo2;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Wagoo2KeybindsClient implements ClientModInitializer {

    public static KeyBinding keybind1;
    public static KeyBinding keybind2;

    @Override
    public void onInitializeClient() {
        // Register keybinds
        keybind1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wagoo2.keybind1",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K, // default key
                "category.wagoo2.keys"
        ));

        keybind2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wagoo2.keybind2",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L, // default key
                "category.wagoo2.keys"
        ));

        // Listen for key presses
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (keybind1.wasPressed()) {
                client.player.networkHandler.sendChatCommand("keybind1");
            }

            if (keybind2.wasPressed()) {
                client.player.networkHandler.sendChatCommand("keybind2");
            }
        });
    }
}
