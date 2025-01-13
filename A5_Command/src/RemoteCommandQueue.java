import java.util.ArrayDeque;
import java.util.Queue;

public class RemoteCommandQueue {
    Queue<Command> commands;
    Command prev = null;

    public RemoteCommandQueue() {
        commands = new ArrayDeque<>();
    }

    /**
     * only add command but not execute
     *
     * @param command
     */
    public void buttonPressed(Command command) {
        commands.add(command);
    }

    /**
     * execute the command in the queue by first-in-first-out principle
     * if there is no command in the queue display "no command"
     */
    public void commandExecute() {
        Command command = commands.poll();
        if (command == null) {
            System.out.println("no command");
        } else {
            prev = command;
            command.execute();
        }

    }

    /**
     * undo the previous command
     */
    public void commandUndo() {
        if (prev == null) {
            System.out.println("No previous command.");
        } else {
            prev.undo();
            prev = null;
        }
    }
}