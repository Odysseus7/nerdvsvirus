package NerdvsVirus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private int jumps = 2;
    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    // weergave van sprite
    public AnimationComponent() {

        animIdle = new AnimationChannel(FXGL.image("nerd.png"), 9, 60, 128, Duration.seconds(1), 0, 0);
        animWalk = new AnimationChannel(FXGL.image("nerd.png"), 9, 60, 128, Duration.seconds(0.8), 1, 8);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21)); // om achteruit te kunnen lopen
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 2;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
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
        if (jumps == 0){
            return;
        }
        physics.setVelocityY(-300);
    }
}