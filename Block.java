public abstract class Block {
    int x, y, length, width;
    int speed;
    String button; // which button corrosponds to a particular block
    int enterTime;
    int receiveTime;
    String level; // each level has it's own preset coordinates, dimensions, etc.

    Block (String level, String button, int enterTime, int receiveTime) {
        this.level = level;
        this.button = button;

        if (this.level.equalsIgnoreCase("easy")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Z")) {
                this.x = 0;
                this.y = 0;
            }
        } else if (this.level.equalsIgnoreCase("medium")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Z")) {
                this.x = 0;
                this.y = 0;
            }
        } else if (this.level.equalsIgnoreCase("hard")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Z")) {
                this.x = 0;
                this.y = 0;
            }
        } else if (this.level.equalsIgnoreCase("boss")) {
            this.length = 1000;
            this.width = 1000;
            this.speed = 1;
            if (this.button.equalsIgnoreCase("A")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("B")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("C")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("X")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Y")) {
                this.x = 0;
                this.y = 0;
            } else if (this.button.equalsIgnoreCase("Z")) {
                this.x = 0;
                this.y = 0;
            }
        }
    }

    public boolean receive(double timeReceived) {
    	int accuracy = Math.abs(receiveTime) - timeReceived;
    	if (accuracy < 1.0) return true;
    	else return false;
    }
    
    /**
	 * Calculates the time that the block should enter, all double parameters
	 */
	public double calculateEnterTime(double speed, double finalTime, double enterX, double enterY, double receiveX, double receiveY) {
		double distanceX, distanceY, distance;
		
		// Calculate the distance in X and Y components
		distanceX = Math.abs(receiveX - enterX);
		distanceY = Math.abs(receiveY - enterY);
		
		// Find the total distance
		distance = Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));
		
		// Find the enter time
		return (finalTime - (distance/speed));
	}
}
