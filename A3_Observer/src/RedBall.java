import java.awt.*;
import java.util.Random;

public class RedBall extends Ball {

    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize, Subject<Ball> subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObserver(this);
    }

    @Override
    public void update(char keyChar) {
        Random random = new Random();
        switch (keyChar) {
            case 'a' -> this.setXSpeed(-random.nextInt(3) - 1);
            case 'd' -> this.setXSpeed(random.nextInt(3) + 1);
            case 'w' -> this.setYSpeed(-random.nextInt(3) - 1);
            case 's' -> this.setYSpeed(random.nextInt(3) + 1);
        }
    }

    @Override
    public void update(Ball whiteBall) {
        if (this.isIntersect(whiteBall)) {
            setXSpeed(whiteBall.getXSpeed());
            setYSpeed(whiteBall.getYSpeed());
        }
    }
}
