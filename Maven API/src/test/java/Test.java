import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.enums.OperationType;

public class Test {
    public static void main(String args[]){
        EPS_API eps_api = new EPS_API(OperationType.CLIENT);
        eps_api.setRemoteAddress("raspberrypi");

        eps_api.init();
    }
}
