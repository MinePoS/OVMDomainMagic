package me.piggypiglet.ovmdm;

import me.piggypiglet.ovmdm.core.framework.BinderModule;

import java.util.Arrays;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://piggypiglet.me
// ------------------------------
public final class OVMDMBootstrap {
    public static void main(String[] args) throws Exception {
        if (args.length >= 1) {
            new BinderModule(OVMDMBootstrap.class).createInjector().getInstance(OVMDomainMagic.class).start(args[0]);
        } else {
            throw new Exception("Port not specified");
        }
    }
}
