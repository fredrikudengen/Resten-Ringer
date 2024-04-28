package no.uib.inf101.sample.entity;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.entity.enemy.Enemy;

public class CollisionDetection {

    private Game game;

    public CollisionDetection(Game game) {
        this.game = game;
    }

    /**
     * Checks for collision between the player and tiles on the game map based on
     * the player's position and direction.
     * 
     * @param player The player object for which collision detection is performed.
     */
    protected void checkTile(Player player) {
        int entityLeftX, entityRightX, entityTopY, entityBottomY;

        if ("left".equals(player.getDirection())) {
            // Player is wider when facing left so left x is further to the right
            entityLeftX = player.getX() + game.getTileSize() + player.getHitBox().x;
            entityRightX = player.getX() + game.getTileSize() + player.getHitBox().x + player.getHitBox().width;
            entityTopY = player.getY();
            entityBottomY = player.getY() + player.getHitBox().height;
        } else if ("right".equals(player.getDirection())) {
            // Player is wider when facing left so right x is further to the left
            entityLeftX = player.getX() + player.getHitBox().x;
            entityRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
            entityTopY = player.getY();
            entityBottomY = player.getY() + player.getHitBox().height;
        } else if ("up".equals(player.getDirection())) {
            // Player is taller when facing up so top y is further down
            entityLeftX = player.getX() + player.getHitBox().x;
            entityRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
            entityTopY = player.getY() + player.getHitBox().height;
            entityBottomY = player.getY() + player.getHitBox().y + player.getHitBox().height;
        } else {
            // Player is taller when facing up so bottom y is further up
            entityLeftX = player.getX() + player.getHitBox().x;
            entityRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
            entityTopY = player.getY();
            entityBottomY = player.getY() + player.getHitBox().height;
        }

        int gridCellNum1, gridCellNum2;

        // Calculates the number of tiles from the edge of the screen
        // Adding or subtracting speed because we want to check tiles before entity
        // collides
        int entityLeftCol = (entityLeftX - player.getSpeed()) / game.getTileSize();
        int entityRightCol = (entityRightX + player.getSpeed()) / game.getTileSize();
        int entityTopRow = (entityTopY - player.getSpeed()) / game.getTileSize();
        int entityBottomRow = (entityBottomY + player.getSpeed()) / game.getTileSize();

        if ("up".equals(player.getDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityTopRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                player.setCollisionOn(true);
            }
        } else if ("down".equals(player.getDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityBottomRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityBottomRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                player.setCollisionOn(true);
            }
        } else if ("left".equals(player.getDirection())) {
            entityLeftCol = (entityLeftX - player.getSpeed()) / game.getTileSize();
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityLeftCol, entityBottomRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                player.setCollisionOn(true);
            }
        } else {
            entityRightCol = (entityRightX + player.getSpeed()) / game.getTileSize();
            gridCellNum1 = getGridCellNumber(entityRightCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityBottomRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                player.setCollisionOn(true);
            }
        }
    }

    /**
     * Checks for collision between the enemy and tiles on the game map based on
     * the player's position and direction. This method is different from the player
     * checkTile because the player sprite is different.
     * 
     * @param enemy The enemy object for which collision detection is performed.
     */
    public void checkTile(Enemy enemy) {
        int entityLeftX = enemy.getX();
        int entityRightX = enemy.getX() + enemy.getHitBox().x + enemy.getHitBox().width;
        int entityTopY = enemy.getY();
        int entityBottomY = enemy.getY() + enemy.getHitBox().height;

        int entityLeftCol = (entityLeftX - enemy.getSpeed()) / game.getTileSize();
        int entityRightCol = (entityRightX + enemy.getSpeed()) / game.getTileSize();
        int entityTopRow = (entityTopY - enemy.getSpeed()) / game.getTileSize();
        int entityBottomRow = (entityBottomY + enemy.getSpeed()) / game.getTileSize();

        int gridCellNum1, gridCellNum2;

        if ("up".equals(enemy.getDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityTopRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                enemy.setCollisionOn(true);
            }
        } else if ("down".equals(enemy.getDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityBottomRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityBottomRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                enemy.setCollisionOn(true);
            }
        } else if ("left".equals(enemy.getDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityLeftCol, entityBottomRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                enemy.setCollisionOn(true);
            }
        } else if ("right".equals(enemy.getDirection())) {
            gridCellNum1 = getGridCellNumber(entityRightCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityBottomRow);
            if (checkCollision(gridCellNum1) || checkCollision(gridCellNum2)) {
                enemy.setCollisionOn(true);
            }
        }
    }

    public int getGridCellNumber(int col, int row) {
        return game.gridFactory.getGridCellNUm()[col][row];
    }

    public boolean checkCollision(int cellNumber) {
        return game.gridFactory.getGridCell()[cellNumber].collision;
    }
}
