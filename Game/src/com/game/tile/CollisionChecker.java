package com.game.tile;

import java.awt.Rectangle;
import java.util.List;

import com.game.entity.Entity;
import com.game.main.Game;
import com.main.level.Level;

public class CollisionChecker {

    private Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkTile(Entity e) {

        Rectangle r = e.getSolidArea();
        int futureX = e.levelX + e.xa * e.playerSpeed;
        int futureY = e.levelY + e.ya * e.playerSpeed;

        checkTileCorner(e, futureX + r.x, futureY + r.y);
        checkTileCorner(e, futureX + r.x + r.width, futureY + r.y);
        checkTileCorner(e, futureX + r.x, futureY + r.y + r.height);
        checkTileCorner(e, futureX + r.x + r.width, futureY + r.y + r.height);
    }

    private void checkTileCorner(Entity e, int px, int py) {
        int tileX = px / Tile.tileSize;
        int tileY = py / Tile.tileSize;

        if (tileX < 0 || tileY < 0 || tileX >= game.level.w || tileY >= game.level.h) {
            e.collisionOn = true;
            return;
        }

        int tileId = game.level.getTile(tileX, tileY);

        if (!game.tileM.tile[tileId].mayPass()) {
            e.collisionOn = true;
        }
    }

    public void checkEntity(Entity self, List<Entity> entities, Rectangle nextRect) {
        for (Entity other : entities) {
            if (other == self) continue;
            if (other.dead) continue;

            Rectangle r = other.getSolidArea();

            if (nextRect.intersects(r)) {
                self.collisionOn = true;
                return;
            }
        }
    }
}
