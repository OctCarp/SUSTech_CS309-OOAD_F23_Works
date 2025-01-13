public class AirConditionerOffCommand implements Command {
    AirConditioner airConditioner;

    public AirConditionerOffCommand(AirConditioner airConditioner) {
        this.airConditioner = airConditioner;
    }

    @Override
    public void execute() {
        airConditioner.off();
    }

    public void undo() {
        airConditioner.on();
    }
}