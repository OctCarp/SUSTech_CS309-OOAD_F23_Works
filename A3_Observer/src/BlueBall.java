import java.awt.*;

public class BlueBall extends Ball{

    public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize, Subject<Ball> subject) {
        super(color, xSpeed, ySpeed, ballSize);
        subject.registerObserver(this);
    }

    @Override
    public void update(char keyChar) {
        this.setXSpeed(-1 * this.getXSpeed());
        this.setYSpeed(-1 * this.getYSpeed());
    }
    public void update(Ball whiteBall){
        if (this.isIntersect(whiteBall)) {
            this.setXSpeed(-1 * this.getXSpeed());
            this.setYSpeed(-1 * this.getYSpeed());
        }
    }
}
