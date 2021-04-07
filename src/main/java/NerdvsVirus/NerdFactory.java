package NerdvsVirus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static NerdvsVirus.NerdType.*;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;


public class NerdFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
        return entityBuilder()
                .from(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .from(data)
                .type(PLAYER)
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .with(new CollidableComponent(true))
                .with(physics)
                .with(new PlayerControl())
                .build();
    }
    @Spawns("spuit")
    public Entity newSpuit(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .from(data)
                .type(SPUIT)
                .viewWithBBox("spuit2.png")
                .scale(0.5, 0.5)
                .with(new CollidableComponent(true))
                .build();
    }


}
