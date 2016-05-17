package mindcar.testing.objects;

import mindcar.testing.ui.UserSettings;

/**
 * Object: This object is used for updating values for different parts of our project
 * based on how it is instantiated.
 * Created by Mattias Landkvist & Nikos Sasopoulos on 3/2/16.
 */
public class SmartCar {
    private Command command;
    private int speed;

    /**
     * Constructs a basic SmarCar with speed set to zero (0) and the Command is STOP
     */
    public SmartCar(){
        command = Command.STOP;
        speed = 0;
    }

    /**
     * @return the command value
     */
    public Command getCommands(){
        return command;
    }

    /**
     * @return the speed value
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Assigns command to this command
     * @param command
     */
    public void setCommand(Command command){
        this.command = command;
    }

    /**
     * Set the speed of this instance
     * @param speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
