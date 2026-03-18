package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import controller.Game;
import controller.GameController;

public class Player extends Entity implements DrawAndUpdate {

    private final Game           game;
    private final GameController gameController;

    /**
     * Facing direction — updated by both movement and shoot keys.
     * Kept separate from {@code movementDirection} (inherited) which is used
     * only for collision correction.
     */
    private Direction facing = Direction.RIGHT;

    private boolean isShocked      = false;
    private long    shockStartTime = 0;
    private static final long SHOCK_DURATION_NS = 1_000_000_000L;
    private static boolean imagesLoaded = false;

    private static BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;
    private static BufferedImage downHit1, downHit2, leftHit1, leftHit2, rightHit1, rightHit2;
    private static BufferedImage beer;

    public Player(Game game, GameController gameController) throws IOException {
        this.game           = game;
        this.gameController = gameController;
        hitBox = new Rectangle(2, 0, 12, 16);
        setDefaultValues();
        getImage();
    }

    @Override
    public void setDefaultValues() {
        x            = game.getScreenWidth()  / 2;
        y            = game.getScreenHeight() / 2;
        healthPoints = 6;
        speed        = 4;
        facing       = Direction.RIGHT;
        isAlive      = true;
        isShocked    = false;
    }

    @Override
    protected void getImage() throws IOException {
        if (imagesLoaded) return;
        up1       = ImageIO.read(getClass().getResourceAsStream("/resources/Up_shotgun_walking_1.png"));
        up2       = ImageIO.read(getClass().getResourceAsStream("/resources/Up_shotgun_walking_2.png"));
        down1     = ImageIO.read(getClass().getResourceAsStream("/resources/Down_shotgun_walking_1.png"));
        down2     = ImageIO.read(getClass().getResourceAsStream("/resources/Down_shotgun_walking_2.png"));
        left1     = ImageIO.read(getClass().getResourceAsStream("/resources/Left_shotgun_walking_1.png"));
        left2     = ImageIO.read(getClass().getResourceAsStream("/resources/Left_shotgun_walking_2.png"));
        right1    = ImageIO.read(getClass().getResourceAsStream("/resources/Right_shotgun_walking_1.png"));
        right2    = ImageIO.read(getClass().getResourceAsStream("/resources/Right_shotgun_walking_2.png"));
        downHit1  = ImageIO.read(getClass().getResourceAsStream("/resources/Down_hit_1.png"));
        downHit2  = ImageIO.read(getClass().getResourceAsStream("/resources/Down_hit_2.png"));
        leftHit1  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_hit_1.png"));
        leftHit2  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_hit_2.png"));
        rightHit1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_hit_1.png"));
        rightHit2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_hit_2.png"));
        beer      = ImageIO.read(getClass().getResourceAsStream("/resources/beer.png"));
        imagesLoaded = true;
    }

    @Override
    public void update() {
        // --- Movement first: update direction and position from input ---
        boolean moving = gameController.isUpPressed()   || gameController.isDownPressed()
                || gameController.isLeftPressed() || gameController.isRightPressed();
        if (moving) {
            if (gameController.isUpPressed()) {
                facing = movementDirection = Direction.UP;
                y -= speed;
            } else if (gameController.isDownPressed()) {
                facing = movementDirection = Direction.DOWN;
                y += speed;
            } else if (gameController.isLeftPressed()) {
                facing = movementDirection = Direction.LEFT;
                x -= speed;
            } else {
                facing = movementDirection = Direction.RIGHT;
                x += speed;
            }
            if (++imageCounter > 10) {
                imageNum     = (imageNum == 1) ? 2 : 1;
                imageCounter = 0;
            }
        }

        // --- Collision correction: undo the move if we stepped into a solid tile ---
        collisionOn = false;
        game.getCollisionDetection().checkTile(this);
        if (collisionOn) {
            switch (movementDirection) {
                case UP    -> y += speed;
                case DOWN  -> y -= speed;
                case LEFT  -> x += speed;
                case RIGHT -> x -= speed;
            }
        }

        // --- Facing from shoot keys (overrides movement facing) ---
        if (gameController.isShotKeyPressed()) {
            facing = gameController.getShotDirection();
        }

        // --- Boundary clamping ---
        int ts = game.getTileSize();
        y = Math.max(20,  Math.min(y, game.getScreenHeight() - 60));
        x = Math.max(5,   Math.min(x, game.getScreenWidth()  - ts - 5));

        // --- Shock timer ---
        if (isShocked && System.nanoTime() - shockStartTime >= SHOCK_DURATION_NS) {
            isShocked = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int ts = game.getTileSize();

        if (isShocked) {
            // Note: no up-hit sprite exists; UP falls through to downHit frames.
            BufferedImage hitImage = switch (facing) {
                case LEFT     -> (imageNum == 1) ? leftHit1  : leftHit2;
                case RIGHT    -> (imageNum == 1) ? rightHit1 : rightHit2;
                case UP, DOWN -> (imageNum == 1) ? downHit1  : downHit2;
            };
            switch (facing) {
                case RIGHT    -> g2.drawImage(hitImage, x,      y, ts * 2, ts,     null);
                case LEFT     -> g2.drawImage(hitImage, x - ts, y, ts * 2, ts,     null);
                case UP, DOWN -> g2.drawImage(hitImage, x,      y, ts,     ts * 2, null);
            }
        } else {
            BufferedImage playerImage = switch (facing) {
                case UP    -> (imageNum == 1) ? up1    : up2;
                case DOWN  -> (imageNum == 1) ? down1  : down2;
                case LEFT  -> (imageNum == 1) ? left1  : left2;
                case RIGHT -> (imageNum == 1) ? right1 : right2;
            };
            switch (facing) {
                case RIGHT -> g2.drawImage(playerImage, x,      y,      ts * 2, ts,     null);
                case LEFT  -> g2.drawImage(playerImage, x - ts, y,      ts * 2, ts,     null);
                case UP    -> g2.drawImage(playerImage, x,      y - ts, ts,     ts * 2, null);
                case DOWN  -> g2.drawImage(playerImage, x,      y,      ts,     ts * 2, null);
            }
        }

        for (int i = 0; i < healthPoints; i++) {
            g2.drawImage(beer, 10 + i * (ts / 2 + 5), 20, ts / 2, ts / 2, null);
        }
    }

    public boolean isShocked() { return isShocked; }
    public boolean isAlive()   { return isAlive; }

    public void setShocked() {
        isShocked      = true;
        shockStartTime = System.nanoTime();
    }
}