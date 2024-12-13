package entity;

import model.Game;
import entity.enemy.Enemy;

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
        int playerLeftX, playerRightX, playerTopY, playerBottomY;

        if ("left".equals(player.getMovementDirection())) {
            // Player is wider when facing left so left x is further to the right
            playerLeftX = player.getX() + game.getTileSize() + player.getHitBox().x;
            playerRightX = player.getX() + game.getTileSize() + player.getHitBox().x + player.getHitBox().width;
            playerTopY = player.getY();
            playerBottomY = player.getY() + player.getHitBox().height;
        } else if ("right".equals(player.getMovementDirection())) {
            // Player is wider when facing left so right x is further to the left
            playerLeftX = player.getX() + player.getHitBox().x;
            playerRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
            playerTopY = player.getY();
            playerBottomY = player.getY() + player.getHitBox().height;
        } else if ("up".equals(player.getMovementDirection())) {
            // Player is taller when facing up so top y is further down
            playerLeftX = player.getX() + player.getHitBox().x;
            playerRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
            playerTopY = player.getY() + player.getHitBox().height;
            playerBottomY = player.getY() + player.getHitBox().y + player.getHitBox().height;
        } else {
            // Player is taller when facing up so bottom y is further up
            playerLeftX = player.getX() + player.getHitBox().x;
            playerRightX = player.getX() + player.getHitBox().x + player.getHitBox().width;
            playerTopY = player.getY();
            playerBottomY = player.getY() + player.getHitBox().height;
        }

        checkCollision(player, playerLeftX, playerRightX, playerTopY, playerBottomY);

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

       checkCollision(enemy, entityLeftX, entityRightX, entityTopY, entityBottomY);
    }

    private void checkCollision(Entity entity, int leftX, int rightX, int topY, int bottomY) {
        int gridCellNum1, gridCellNum2;

        // Calculates the number of tiles from the edge of the screen
        // Adding or subtracting speed because we want to check tiles before entity
        // collides
        int entityLeftCol = (leftX - entity.getSpeed()) / game.getTileSize();
        int entityRightCol = (rightX + entity.getSpeed()) / game.getTileSize();
        int entityTopRow = (topY - entity.getSpeed()) / game.getTileSize();
        int entityBottomRow = (bottomY + entity.getSpeed()) / game.getTileSize();

        if ("up".equals(entity.getMovementDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityTopRow);
            if (checkCollisionOnGridCell(gridCellNum1) || checkCollisionOnGridCell(gridCellNum2)) {
                entity.setCollisionOn(true);
            }
        } else if ("down".equals(entity.getMovementDirection())) {
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityBottomRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityBottomRow);
            if (checkCollisionOnGridCell(gridCellNum1) || checkCollisionOnGridCell(gridCellNum2)) {
                entity.setCollisionOn(true);
            }
        } else if ("left".equals(entity.getMovementDirection())) {
            entityLeftCol = (leftX - entity.getSpeed()) / game.getTileSize();
            gridCellNum1 = getGridCellNumber(entityLeftCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityLeftCol, entityBottomRow);
            if (checkCollisionOnGridCell(gridCellNum1) || checkCollisionOnGridCell(gridCellNum2)) {
                entity.setCollisionOn(true);
            }
        } else {
            entityRightCol = (rightX + entity.getSpeed()) / game.getTileSize();
            gridCellNum1 = getGridCellNumber(entityRightCol, entityTopRow);
            gridCellNum2 = getGridCellNumber(entityRightCol, entityBottomRow);
            if (checkCollisionOnGridCell(gridCellNum1) || checkCollisionOnGridCell(gridCellNum2)) {
                entity.setCollisionOn(true);
            }
        }
    }

    public int getGridCellNumber(int col, int row) {
        return game.gridFactory.getGridCellNUm()[col][row];
    }

    public boolean checkCollisionOnGridCell(int cellNumber) {
        return game.gridFactory.getGridCell()[cellNumber].collision;
    }
}
