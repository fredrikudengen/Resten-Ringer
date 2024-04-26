package no.uib.inf101.sample;

import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.enemy.Enemy;

public class CollisionDetection {

    private Game game;

    public CollisionDetection(Game game) {
        this.game = game;
    }

    public void checkTile(Player player) {

        int entityLeftX = player.getX() + player.getHitBox().x;
        int entityRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
        int entityTopY = player.getY() + player.getHitBox().y;
        int entityBottomY = player.getY() + player.getHitBox().y + player.getHitBox().height;

        int entityLeftCol = entityLeftX / game.getTileSize();
        int entityRightCol = entityRightX / game.getTileSize();
        int entityTopRow = entityTopY / game.getTileSize();
        int entityBottomRow = entityBottomY / game.getTileSize();

        int tileNum1, tileNum2;

        if (player.getDirection() == "up") {
            entityTopRow = (entityTopY - player.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityRightCol][entityTopRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                player.setCollisionOn(true);
            }
        }
        if (player.getDirection() == "down") {
            entityBottomRow = (entityBottomY + player.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityRightCol][entityBottomRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                player.setCollisionOn(true); 
            }
        }
        if (player.getDirection() == "left") {
            entityLeftCol = (entityLeftX - player.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityBottomRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                player.setCollisionOn(true);
            }
        }
        if (player.getDirection() == "right") {
            entityRightCol = (entityRightX + player.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityRightCol][entityTopRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityRightCol][entityBottomRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                player.setCollisionOn(true); 
            }
        }
    }

    public void checkTile(Enemy enemy) {

        int entityLeftX = enemy.getX() + enemy.getHitBox().x;
        int entityRightX = enemy.getX() + enemy.getHitBox().x + enemy.getHitBox().width;
        int entityTopY = enemy.getY() + enemy.getHitBox().y;
        int entityBottomY = enemy.getY() + enemy.getHitBox().y + enemy.getHitBox().height;

        int entityLeftCol = entityLeftX / game.getTileSize();
        int entityRightCol = entityRightX / game.getTileSize();
        int entityTopRow = entityTopY / game.getTileSize();
        int entityBottomRow = entityBottomY / game.getTileSize();

        int tileNum1, tileNum2;

        if (enemy.getDirection() == "up") {
            entityTopRow = (entityTopY - enemy.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityRightCol][entityTopRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                enemy.setCollisionOn(true); 
            }
        }
        if (enemy.getDirection() == "down") {
            entityBottomRow = (entityBottomY + enemy.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityRightCol][entityBottomRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                enemy.setCollisionOn(true); 
            }
        }
        if (enemy.getDirection() == "left") {
            entityLeftCol = (entityLeftX - enemy.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityTopRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityLeftCol][entityBottomRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                enemy.setCollisionOn(true); 
            }
        }
        if (enemy.getDirection() == "right") {
            entityRightCol = (entityRightX + enemy.getSpeed()) / game.getTileSize();
            tileNum1 = game.tileFactory.getGridCellNUm()[entityRightCol][entityTopRow];
            tileNum2 = game.tileFactory.getGridCellNUm()[entityRightCol][entityBottomRow];
            if (game.tileFactory.getGridCell()[tileNum1].collision == true
                    || game.tileFactory.getGridCell()[tileNum2].collision == true) {
                enemy.setCollisionOn(true); 
            }
        }
    }
}
