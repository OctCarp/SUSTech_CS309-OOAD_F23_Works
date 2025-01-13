public class AirConditionerOnCommand implements Command {
    AirConditioner airConditioner;

    public AirConditionerOnCommand(AirConditioner airConditioner) {
        this.airConditioner = airConditioner;
    }

    @Override
    public void execute() {
        airConditioner.on();
    }

    public void undo() {
        airConditioner.off();
    }
}