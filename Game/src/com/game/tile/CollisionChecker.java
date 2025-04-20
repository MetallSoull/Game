package com.game.tile;

import java.awt.Rectangle;
import java.util.List;

import com.game.entity.Entity;
import com.game.main.Game;

public class CollisionChecker {

    Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkTile(Entity entity) {

        int entityLeftCol = (entity.levelX + entity.solidArea.x) / Tile.tileSize;
        int entityRightCol = (entity.levelX + entity.solidArea.x + entity.solidArea.width) / Tile.tileSize;
        int entityTopRow = (entity.levelY + entity.solidArea.y) / Tile.tileSize;
        int entityBottomRow = (entity.levelY + entity.solidArea.y + entity.solidArea.height) / Tile.tileSize;

        int tileNum1, tileNum2;

        try {
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.collisionOn = true;
        }
    }

    public void overloadedCheckTile(Entity entity, Rectangle nextSolidArea) {

        int nextLeftCol = (nextSolidArea.x) / Tile.tileSize;
        int nextRightCol = (nextSolidArea.x + nextSolidArea.width) / Tile.tileSize;
        int nextTopRow = (nextSolidArea.y) / Tile.tileSize;
        int nextBottomRow = (nextSolidArea.y + nextSolidArea.height) / Tile.tileSize;

        int tileNum1, tileNum2;

        try {
            tileNum1 = game.tileM.mapTileNum[nextLeftCol][nextTopRow];
            tileNum2 = game.tileM.mapTileNum[nextRightCol][nextTopRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            tileNum1 = game.tileM.mapTileNum[nextLeftCol][nextBottomRow];
            tileNum2 = game.tileM.mapTileNum[nextRightCol][nextBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity.collisionOn = true;
        }
    }

    public void checkEntity(Entity entity, List<Entity> target, Rectangle nextSolidArea) {
        int entityGridX = nextSolidArea.x / game.gridCellSize;
        int entityGridY = nextSolidArea.y / game.gridCellSize;

        for (int x = entityGridX - 1; x <= entityGridX + 1; x++) {
            for (int y = entityGridY - 1; y <= entityGridY + 1; y++) {
                if (x >= 0 && x < game.entityGrid.length && y >= 0 && y < game.entityGrid[x].length) {
                    List<Entity> possibleColliders = game.entityGrid[x][y];
                    for (Entity otherEntity : possibleColliders) {
                        if (otherEntity != entity) {

                            // Predict the future rectangle of the moving entity
                            int predictedX = otherEntity.levelX + otherEntity.xa * otherEntity.playerSpeed;
                            int predictedY = otherEntity.levelY + otherEntity.ya * otherEntity.playerSpeed;

                            Rectangle targetSolidArea = new Rectangle(
                                predictedX + otherEntity.solidArea.x + 1,
                                predictedY + otherEntity.solidArea.y + 1,
                                otherEntity.solidArea.width - 2,
                                otherEntity.solidArea.height - 2
                            );

                            if (nextSolidArea.intersects(targetSolidArea)) {
                                entity.collisionOn = true;
                                entity.collidedEntity = otherEntity;
                                otherEntity.collidedEntity = entity;
                                return; 
                            }
                        }
                    }
                }
            }
        }
    }
}