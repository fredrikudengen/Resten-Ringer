package model.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Holds the two animation frames for each movement direction of one enemy type.
 *
 * <p>Fallback directions (e.g. NormalEnemy reusing DOWN sprites for LEFT/RIGHT)
 * are baked in at construction time by passing the same array for multiple
 * directions. This keeps the fallback logic out of draw() and out of each
 * subclass — it is declared once, where the sprites are defined.
 *
 * <p>Use {@link #load(Class, String, String)} to load a pair of frames safely.
 */
public final class SpriteSet {

    private final BufferedImage[] up;
    private final BufferedImage[] down;
    private final BufferedImage[] left;
    private final BufferedImage[] right;

    /**
     * @param up    two-element array: [frame1, frame2] for upward movement
     * @param down  two-element array for downward movement (may alias another array for fallback)
     * @param left  two-element array for leftward movement
     * @param right two-element array for rightward movement
     */
    public SpriteSet(
            BufferedImage[] up,
            BufferedImage[] down,
            BufferedImage[] left,
            BufferedImage[] right) {
        this.up    = up;
        this.down  = down;
        this.left  = left;
        this.right = right;
    }

    /**
     * Returns the correct frame for the given direction and animation counter.
     *
     * @param dir     current movement direction
     * @param frameNum 1 or 2
     */
    public BufferedImage get(Entity.Direction dir, int frameNum) {
        BufferedImage[] frames = switch (dir) {
            case UP    -> up;
            case DOWN  -> down;
            case LEFT  -> left;
            case RIGHT -> right;
        };
        return frames[frameNum - 1];
    }

    // -------------------------------------------------------------------------
    // Factory helpers
    // -------------------------------------------------------------------------

    /**
     * Loads two sprite frames from the classpath.
     * Throws {@link IOException} so callers (static initializers) can wrap it
     * as {@link ExceptionInInitializerError} and fail loudly at class-load time.
     *
     * @param context   class used to locate the resource (typically the entity class itself)
     * @param path1     classpath path to frame 1
     * @param path2     classpath path to frame 2
     * @return          two-element {@code BufferedImage[]} ready for use in a {@code SpriteSet}
     */
    public static BufferedImage[] load(Class<?> context, String path1, String path2)
            throws IOException {
        return new BufferedImage[]{
                read(context, path1),
                read(context, path2)
        };
    }

    /**
     * Loads a single image from the classpath (e.g. a HUD icon like the beer sprite).
     *
     * @param context class used to locate the resource
     * @param path    classpath path to the image
     */
    public static BufferedImage loadSingle(Class<?> context, String path) throws IOException {
        return read(context, path);
    }

    private static BufferedImage read(Class<?> context, String path) throws IOException {
        try (InputStream is = context.getResourceAsStream(path)) {
            if (is == null) throw new IOException("Resource not found: " + path);
            return ImageIO.read(is);
        }
    }
}