package me.piggypiglet.ovmdm.core.objects;

import com.google.inject.Inject;
import me.piggypiglet.ovmdm.OVMDomainMagic;
import me.piggypiglet.ovmdm.core.storage.GFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://piggypiglet.me
// ------------------------------
public final class RunCommands implements Runnable {
    @Inject private GFile gFile;
    @Inject private OVMDomainMagic main;

    @Override
    public void run() {
        List<String> domains = gFile.getFileConfiguration("config").getStringList("domains");
        Runtime runtime = Runtime.getRuntime();

        try {
            if (read(runtime.exec("id -u").getInputStream()).equalsIgnoreCase("0")) {
                runtime.exec("service nginx stop");

                domains.forEach(d -> {
                    try {
                        runtime.exec("letsencrypt certonly -d " + String.format(d, main.getPort()) +
                                //TODO: Add in the ssl folder.
                                " && VBoxManage guestcontrol \"MinePoS Ubuntu\" copyto -R ssl folder --target-directory /etc/letsencrypt/.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                throw new Exception("Program must be ran as root.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String read(InputStream inputStream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
