package model.entities;

import java.awt.Rectangle;

import model.world.Game;

public class CollisionDetection {

    private final Game game;

    public CollisionDetection(Game game) {
        this.game = game;
    }

    // -------------------------------------------------------------------------
    // Tile collision
    // -------------------------------------------------------------------------

    /**
     * Checks whether {@code entity} has moved into a solid tile.
     *
     * <p>The entity is expected to have already applied its movement for this tick.
     * {@code getWorldHitBox()} provides a consistent, correctly-offset bounding
     * rectangle for every entity type — so Player, Enemy, and Projectile all go
     * through the same path with no per-type branching.
     *
     * <p>Only the leading edge in the movement direction is tested:
     * <ul>
     *   <li>UP/DOWN    → top or bottom row of tiles touched by the hitbox</li>
     *   <li>LEFT/RIGHT → left or right column of tiles touched by the hitbox</li>
     * </ul>
     *
     * @param entity any entity whose {@code movementDirection} and {@code hitBox} are set
     * @return {@code true} if the leading edge overlaps a solid tile
     */
    public boolean checkTile(Entity entity) {
        Rectangle bounds = entity.getWorldHitBox();
        int ts = game.getTileSize();

        int leftCol   = bounds.x                     / ts;
        int rightCol  = (bounds.x + bounds.width)    / ts;
        int topRow    = bounds.y                     / ts;
        int bottomRow = (bounds.y + bounds.height)   / ts;

        return switch (entity.getMovementDirection()) {
            case UP    -> isSolid(leftCol, topRow)    || isSolid(rightCol, topRow);
            case DOWN  -> isSolid(leftCol, bottomRow) || isSolid(rightCol, bottomRow);
            case LEFT  -> isSolid(leftCol, topRow)    || isSolid(leftCol, bottomRow);
            case RIGHT -> isSolid(rightCol, topRow)   || isSolid(rightCol, bottomRow);
        };
    }

    /**
     * Returns true if the tile at (col, row) is solid.
     * Out-of-bounds coordinates are treated as solid so entities cannot walk
     * off the edge of the map.
     */
    private boolean isSolid(int col, int row) {
        if (col < 0 || col >= game.getCols() || row < 0 || row >= game.getRows()) {
            return true;
        }
        // Cache factory reference — previously called twice per isSolid() invocation.
        var grid = game.getGridFactory();
        return grid.getGridCell()[grid.getGridCellNum()[col][row]].isCollision();
    }

    // -------------------------------------------------------------------------
    // Entity-vs-entity collision
    // -------------------------------------------------------------------------

    /**
     * Returns true if the world-space hitboxes of two entities overlap.
     *
     * <p>Moved here from Entity where it previously lived as {@code overlaps(Entity)}.
     * Collision detection logic belongs in this class, not on the data/behaviour
     * type Entity. Callers that previously wrote {@code this.overlaps(other)} should
     * now write {@code game.getCollisionDetection().entitiesOverlap(this, other)}.
     *
     * @param a first entity (must have a non-null hitBox)
     * @param b second entity (must have a non-null hitBox)
     * @return {@code true} if their world-space hitboxes intersect
     */
    public boolean entitiesOverlap(Entity a, Entity b) {
        return a.getWorldHitBox().intersects(b.getWorldHitBox());
    }

    // -------------------------------------------------------------------------
    // Internal geometry
    // -------------------------------------------------------------------------

    /**
     * Standard AABB overlap test defined by corner coordinates.
     *
     * <p>Moved here from Entity where it was a {@code protected static} method —
     * a collision algorithm has no business living on a data class. Now private:
     * external callers should use {@link #entitiesOverlap} or {@link #checkTile},
     * which take typed Entity arguments rather than eight raw ints.
     *
     * <p>The min/max normalisation means callers may pass corners in any order.
     */
    static boolean rectanglesOverlap(int x1, int y1, int x2, int y2,
                                     int x3, int y3, int x4, int y4) {
        int aLeft   = Math.min(x1, x2);  int aRight  = Math.max(x1, x2);
        int aTop    = Math.min(y1, y2);  int aBottom = Math.max(y1, y2);
        int bLeft   = Math.min(x3, x4);  int bRight  = Math.max(x3, x4);
        int bTop    = Math.min(y3, y4);  int bBottom = Math.max(y3, y4);
        return !(aRight < bLeft || bRight < aLeft || aBottom < bTop || bBottom < aTop);
    }
}