package mindcar.testing;

/**
 * Utility class for the Command enum
 * Created by Mattias Landkvist & Nikos Sasopoulos on 3/2/16.
 */
public class CommandUtils {

    /**
     * Converts a Command into a byte[]
     * @param command
     * @return byte[] of a Command
     */
    public static byte[] toByteArray(Command command){
        return command.name().getBytes();
    }

}
