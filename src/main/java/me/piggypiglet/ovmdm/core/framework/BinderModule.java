package me.piggypiglet.ovmdm.core.framework;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.piggypiglet.ovmdm.OVMDMBootstrap;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://piggypiglet.me
// ------------------------------
public final class BinderModule extends AbstractModule {
    private final Class clazz;

    public BinderModule(final Class clazz) {
        this.clazz = clazz;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure() {
        bind(clazz).toInstance(clazz);
    }
}
