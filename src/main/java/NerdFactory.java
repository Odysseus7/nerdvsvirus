package NerdGame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.*;

public class NerdFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data){
        return FXGL.entityBuilder()
                .vieuw(texture(""))
                .build();
    }
}
