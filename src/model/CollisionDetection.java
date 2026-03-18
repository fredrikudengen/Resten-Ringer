package model;

import controller.Game;
import model.Entity.Direction;
import model.enemies.Enemy;

public class CollisionDetection {

    private final Game game;

    public CollisionDetection(Game game) {
        this.game = game;
    }

    /**
     * Checks tile collision for the player.
     *
     * Key insight: 'x' and 'y' are the player's body anchor, not the sprite
     * top-left corner. The sprite extends in different directions around this
     * anchor depending on facing direction, but the body hitbox is always at
     * the same position relative to (x, y). Therefore all four directions
     * produce an identical hitbox — no direction-specific branching is needed.
     *
     * Previous bugs:
     *   LEFT:  had leftX = x + tileSize + hitBox.x, placing the check ~one tile
     *          to the RIGHT of the player. The player could walk into left-facing
     *          walls undetected, land inside them, and become permanently stuck.
     *   UP:    had topY = y + hitBox.height instead of y + hitBox.y, so topY
     *          equalled bottomY (zero-height box, only one row checked).
     *
     * @param player the player to check
     */
    public void checkTile(Player player) {
        int leftX   = player.getX() + player.getHitBox().x;
        int rightX  = leftX + player.getHitBox().width;
        int topY    = player.getY() + player.getHitBox().y;
        int bottomY = topY + player.getHitBox().height;

        checkCollision(player, leftX, rightX, topY, bottomY);
    }

    /**
     * Checks tile collision for an enemy.
     *
     * @param enemy the enemy to check
     */
    public void checkTile(Enemy enemy) {
        int leftX   = enemy.getX();
        int rightX  = enemy.getX() + enemy.getHitBox().x + enemy.getHitBox().width;
        int topY    = enemy.getY();
        int bottomY = enemy.getY() + enemy.getHitBox().height;

        checkCollision(enemy, leftX, rightX, topY, bottomY);
    }

    /**
     * Checks whether the entity's already-moved position overlaps a solid tile.
     *
     * Player.update() moves the entity FIRST, then calls checkTile. By the time
     * we arrive here the coordinates already reflect the new position — there is
     * no need for an additional speed-based lookahead. The previous code subtracted
     * (or added) 'speed' a second time, which effectively looked back toward the
     * pre-move position and could miss walls the entity had just stepped into.
     *
     * Only the edge relevant to the current direction of movement is tested:
     *   UP/DOWN    → top or bottom row of the hitbox
     *   LEFT/RIGHT → left or right column of the hitbox
     */
    private void checkCollision(Entity entity, int leftX, int rightX, int topY, int bottomY) {
        int ts = game.getTileSize();

        int leftCol   = leftX   / ts;
        int rightCol  = rightX  / ts;
        int topRow    = topY    / ts;
        int bottomRow = bottomY / ts;

        switch (entity.getMovementDirection()) {
            case UP    -> { if (isSolid(leftCol, topRow)    || isSolid(rightCol, topRow))    entity.setCollisionOn(true); }
            case DOWN  -> { if (isSolid(leftCol, bottomRow) || isSolid(rightCol, bottomRow)) entity.setCollisionOn(true); }
            case LEFT  -> { if (isSolid(leftCol, topRow)    || isSolid(leftCol, bottomRow))  entity.setCollisionOn(true); }
            case RIGHT -> { if (isSolid(rightCol, topRow)   || isSolid(rightCol, bottomRow)) entity.setCollisionOn(true); }
        }
    }

    private boolean isSolid(int col, int row) {
        int cellNumber = game.getGridFactory().getGridCellNum()[col][row];
        return game.getGridFactory().getGridCell()[cellNumber].isCollision();
    }
}