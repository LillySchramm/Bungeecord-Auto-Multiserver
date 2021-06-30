package de.epsdev.bungeeautoserver.api.packages;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.packages.packages.Base_Package;
import de.epsdev.packages.packages.Package;

import java.net.Socket;

public class RespondRegisterPackage extends Package {
    public RespondRegisterPackage(Base_Package base_package) {
        super(base_package);
    }

    public RespondRegisterPackage(String name) {
        super("RespondRegisterPackage");

        add("name", name);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        EPS_API.NAME = getString("name");
    }
}
