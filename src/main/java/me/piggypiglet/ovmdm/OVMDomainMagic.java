package me.piggypiglet.ovmdm;

import com.google.inject.Inject;
import lombok.Getter;
import me.piggypiglet.ovmdm.core.objects.RunCommands;
import me.piggypiglet.ovmdm.core.objects.enums.Registerables;
import me.piggypiglet.ovmdm.core.storage.GFile;

import java.util.stream.Stream;

import static me.piggypiglet.ovmdm.core.objects.enums.Registerables.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://piggypiglet.me
// ------------------------------
public final class OVMDomainMagic {
    @Inject private GFile gFile;
    @Inject private RunCommands runCommands;

    @Getter private String port;

    void start(String port) {
        this.port = port;

        Stream.of(
                FILES, COMMANDS
        ).forEach(this::register);
    }

    private void register(Registerables registerable) {
        switch (registerable) {
            case FILES:
                gFile.make("config", "./config.json", "/config.json");

                break;

            case COMMANDS:
                runCommands.run();

                break;
        }
    }
}
