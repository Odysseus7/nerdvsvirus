package NerdvsVirus;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private int speed = 0;
    private int jumps = 2;
    private PhysicsComponent physics;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;


    //weergave van sprite
    public AnimationComponent() {

        animIdle = new AnimationChannel(FXGL.image("sprite.png"), 8, 75, 168, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(FXGL.image("sprite.png"), 8, 75, 168, Duration.seconds(0.8), 0, 7);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
//        entity.setView(texture);
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21)); // om achteruit te kunnen lopen
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 1;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);
        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
//            speed = (int) (speed * 0.9);
        }else{
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void right() {
        getEntity().setScaleX(1);
        physics.setVelocityX(150);
    }

    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-150);
    }
    public void stop(){
        physics.setVelocityX(0);
    }
    public void jump(){
        physics.setVelocityY(-300);
    }
}