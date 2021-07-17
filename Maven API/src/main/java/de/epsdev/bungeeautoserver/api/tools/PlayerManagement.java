package de.epsdev.bungeeautoserver.api.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class PlayerManagement {
    public static String getUUID(String playername){
        String ret = request("https://api.mojang.com/users/profiles/minecraft/" + playername);

        // It's dirty but it works

        return ret.split("\"")[7].replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
        );
    }

    private static String request(String s_url)  {
        String tmp = null;

        try {
            URL url = new URL(s_url);
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            urlConnection.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                tmp += inputLine;
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return tmp;
    }
}
