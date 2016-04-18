package mindcar.testing.objects;

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
        command = Command.s;
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
     * Set the Command of this instance according to the EEGObject
     * @param eeg
     */
    public void setCommand(EEGObject eeg){
        if(eeg.getAttention() >= 40) {
            this.command = Command.f;
        } else {
            this.command = Command.s;
        }
    }

    /**
     * Set the speed of this instance
     * @param speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
