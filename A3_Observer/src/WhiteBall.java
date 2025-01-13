import java.awt.*;

public class WhiteBall extends Ball {

    public WhiteBall(Color color, int xSpeed, int ySpeed, int ballSize, Subject<Ball> subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObserver(this);
    }

    @Override
    public void update(char keyChar) {
        switch (keyChar) {
            case 'a' -> this.setXSpeed(-8);
            case 'd' -> this.setXSpeed(8);
            case 'w' -> this.setYSpeed(-8);
            case 's' -> this.setYSpeed(8);
        }
    }

    public void update(Ball whiteBall) {
    }
}
