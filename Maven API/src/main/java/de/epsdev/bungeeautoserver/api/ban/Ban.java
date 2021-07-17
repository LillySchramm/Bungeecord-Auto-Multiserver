package de.epsdev.bungeeautoserver.api.ban;

import java.time.Instant;
import java.util.HashMap;

public class Ban {

    public final String uuid;
    public final String reason;

    public final long until;

    public static HashMap<String, Ban> bans = new HashMap<>();

    public Ban(String uuid, long until, String reason){
        this.until = until;
        this.uuid = uuid;
        this.reason = reason;

        bans.put(uuid, this);
    }

    public String getBanString(){
        long cur_time = Instant.now().getEpochSecond();

        if(cur_time >= until){
            return "";
        }

        double delta = until - cur_time;
        String ret = "";

        double months = Math.floor(delta / (60 * 60 * 24 * 30));
        if (months != 0){
            ret += (int) months + " m ";
        }
        delta -= months * (60 * 60 * 24 * 30);

        double days = Math.floor(delta / (60 * 60 * 24));
        delta -= days * (60 * 60 * 24);
        ret += (int) days  + " d ";

        double hours = Math.floor(delta / (60 * 60));
        delta -= hours * (60 * 60);
        ret += (int) hours + " h ";

        double minutes = Math.floor(delta / 60);
        delta -= minutes * 60;
        ret += (int) minutes + " min ";

        ret += (int) delta + " s ";

        return ret;
    }

    public static Ban getBanned(String uuid){
        return bans.getOrDefault(uuid, null);
    }

}
