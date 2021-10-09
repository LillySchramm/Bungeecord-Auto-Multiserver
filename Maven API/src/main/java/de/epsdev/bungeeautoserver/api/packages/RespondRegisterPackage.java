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

        add("ftp_server", EPS_API.ftpServerAddress);
        add("ftp_port", EPS_API.ftpServerPort);
        add("ftp_user", EPS_API.ftpServerUser);
        add("ftp_password", EPS_API.ftpServerPassword);
    }

    @Override
    public void onPackageReceive(Socket socket, Object o) {
        EPS_API.NAME = getString("name");

        EPS_API.ftpServerAddress = getString("ftp_server");
        EPS_API.ftpServerPort = getInteger("ftp_port");
        EPS_API.ftpServerUser = getString("ftp_user");
        EPS_API.ftpServerPassword = getString("ftp_password");
    }
}
