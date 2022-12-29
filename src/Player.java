public class Player {
    String name;
    int lives;
    int guess;
    boolean isOut;

    public Player(String name, int lives, int guess, boolean isOut) {
        this.name = name;
        this.lives = lives;
        this.guess = guess;
        this.isOut = isOut;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }
}
