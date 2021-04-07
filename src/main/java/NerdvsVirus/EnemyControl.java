package NerdvsVirus;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

public class EnemyControl extends Component{

    private PhysicsComponent physics;

    private LocalTimer jumpTimer;

    @Override
    public void onAdded(){
        jumpTimer = FXGL.newLocalTimer();
        jumpTimer.capture();
    }

    @Override
    public void onUpdate(double tpf){
        if (jumpTimer.elapsed(Duration.seconds(3))){
            jump();
            jumpTimer.capture();
        }

    }


    public void jump(){
        physics.setVelocityY(-300);
    }
}
