package no.uib.inf101.sample;

public class Projectile {

    private int projectileX;
    private int projectileY;
    private int projectileSpeed;
    private double normalizedDirectionX;
    private double normalizedDirectionY;

    Game game;

    public Projectile(int x, int y, int speed, Game game) {
        this.projectileX = x;
        this.projectileY = y;
        this.projectileSpeed = speed;
        this.game = game;
    }

    public void update() {
        // No need to update position here, as it's handled in handleMouseClick
        // Update the position of the projectile based on its speed and direction
        projectileX += normalizedDirectionX * projectileSpeed;
        projectileY += normalizedDirectionY * projectileSpeed;
    }

    public void handleMouseClick(int mouseX, int mouseY) {
        // Calculate the direction vector from current position to the clicked position
        double directionX = mouseX - this.projectileX;
        double directionY = mouseY - this.projectileY;

        // Calculate the magnitude of the direction vector
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

        // Normalize the direction vector
        double normalizedDirectionX = directionX / magnitude;
        double normalizedDirectionY = directionY / magnitude;
        System.out.println("Direction is: " + normalizedDirectionX + "\n" + normalizedDirectionY);

        // Update the position of the projectile using the direction vector and speed
        this.projectileX += (int) (normalizedDirectionX * projectileSpeed);
        this.projectileY += (int) (normalizedDirectionY * projectileSpeed);

        // Add the projectile to the game's list of projectiles
        game.addProjectile(this);
    }

    // Getters and setters
    public int getProjectileX() {
        return projectileX;
    }

    public void setProjectileX(int x) {
        this.projectileX = x;
    }

    public int getProjectileY() {
        return projectileY;
    }

    public void setProjectileY(int y) {
        this.projectileY = y;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(int speed) {
        this.projectileSpeed = speed;
    }
}
