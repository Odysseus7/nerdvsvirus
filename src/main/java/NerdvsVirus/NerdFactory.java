package NerdvsVirus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.LiftComponent;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static NerdvsVirus.NerdType.*;
import static com.almasb.fxgl.dsl.FXGL.*;


public class NerdFactory implements EntityFactory {
    @Spawns("background1")
    public Entity newBackground(SpawnData data){
        return entityBuilder()
                .view(new ScrollingBackgroundView(texture("bg1.jpg")))
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }
    @Spawns("background2")
    public Entity newBackground2(SpawnData data){
        return entityBuilder()
                .view(new ScrollingBackgroundView(texture("bg2.jpg")))
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }
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
        //physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        return FXGL.entityBuilder()
                .type(PLAYER)
                .bbox(new HitBox(new Point2D(5,5), BoundingShape.circle(5)))
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(75, 168)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .with(new AnimationComponent())
                .scale(0.5, 0.5)
                .build();


    }@Spawns("enemy")
    public Entity newEnemy(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        return FXGL.entityBuilder()
                .type(ENEMY)
                .from(data)
                .viewWithBBox("bacteria.png")
                .scale(0.5, 0.5)
                .bbox(new HitBox(BoundingShape.box(30, 30)))
                .with(physics)
                .with(new EnemyControl())
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
    @Spawns("door")
    public Entity newDoor(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .from(data)
                .type(DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    //nog in de maak! niet af @LUUK
    @Spawns("keyCode")
    public Entity newKeyCode(SpawnData data) {
               return entityBuilder(data)
                .zIndex(100)
                .build();
    }
    @Spawns("button")
    public Entity newButton(SpawnData data){
        var keyEntity = FXGL.getGameWorld().create("keyCode", new SpawnData(data.getX(), data.getY() - 50).put("Hoi", "E"));
        keyEntity.getViewComponent().opacityProperty().setValue(0);
        return FXGL.entityBuilder()
                .type(BUTTON)
                .from(data)
                .viewWithBBox(new Rectangle(20, 20, Color.GREEN))
                .with(new CollidableComponent(true))
                .with("keyCode", keyEntity)
                .build();

    }


}
