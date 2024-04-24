package no.uib.inf101.sample;

import no.uib.inf101.sample.entity.Entity;
import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.enemy.Enemy;

public class CollisionDetection {

    Game game;

    public CollisionDetection(Game game) {
        this.game = game;
    }

    public void checkTile(Player player) {

        int entityLeftX = player.x + player.hitBox.x;
        int entityRightX = player.x + player.hitBox.x + player.hitBox.width;
        int entityTopY = player.y + player.hitBox.y;
        int entityBottomY = player.y + player.hitBox.y + player.hitBox.height;

        int entityLeftCol = entityLeftX / game.tileSize;
        int entityRightCol = entityRightX / game.tileSize;
        int entityTopRow = entityTopY / game.tileSize;
        int entityBottomRow = entityBottomY / game.tileSize;

        int tileNum1, tileNum2;

        if (player.movementDirection == "up") {
            entityTopRow = (entityTopY - player.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.mapTileNum[entityRightCol][entityTopRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                player.collisionOn = true;
            }
        }
        if (player.movementDirection == "down") {
            entityBottomRow = (entityBottomY + player.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileFactory.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                player.collisionOn = true;
            }
        }
        if (player.movementDirection == "left") {
            entityLeftCol = (entityLeftX - player.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.mapTileNum[entityLeftCol][entityBottomRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                player.collisionOn = true;
            }
        }
        if (player.movementDirection == "right") {
            entityRightCol = (entityRightX + player.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = game.tileFactory.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                player.collisionOn = true;
            }
        }
    }

    public void checkTile(Enemy enemy) {


        int entityLeftX = enemy.x + enemy.hitBox.x;
        int entityRightX = enemy.x + enemy.hitBox.x + enemy.hitBox.width;
        int entityTopY = enemy.y + enemy.hitBox.y;
        int entityBottomY = enemy.y + enemy.hitBox.y + enemy.hitBox.height;

        int entityLeftCol = entityLeftX / game.tileSize;
        int entityRightCol = entityRightX / game.tileSize;
        int entityTopRow = entityTopY / game.tileSize;
        int entityBottomRow = entityBottomY / game.tileSize;

        System.out.println("entityBottomRow = " + entityBottomRow);
        System.out.println("entityLeftCol = " + entityLeftCol);
        System.out.println("entityRightCol = "+ entityRightCol);
        System.out.println("entityTopRow = "+ entityTopRow);
        
        int tileNum1, tileNum2;

        if (enemy.movementDirection == "up") {
            entityTopRow = (entityTopY - enemy.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.mapTileNum[entityRightCol][entityTopRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                enemy.collisionOn = true;
            }
        }
        if (enemy.movementDirection == "down") {
            entityBottomRow = (entityBottomY + enemy.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileFactory.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                enemy.collisionOn = true;
            }
        }
        if (enemy.movementDirection == "left") {
            entityLeftCol = (entityLeftX - enemy.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.mapTileNum[entityLeftCol][entityBottomRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                enemy.collisionOn = true;
            }
        }
        if (enemy.movementDirection == "right") {
            entityRightCol = (entityRightX + enemy.speed) / game.tileSize;
            tileNum1 = game.tileFactory.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = game.tileFactory.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileFactory.tile[tileNum1].collision == true
                    || game.tileFactory.tile[tileNum2].collision == true) {
                enemy.collisionOn = true;
            }
        }
    }
}
