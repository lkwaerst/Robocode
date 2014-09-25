import robocode.*;
//dette er en endring
public class AimBot extends AdvancedRobot {
    
   public void run() {
       setAdjustRadarForRobotTurn(true);
       setAdjustRadarForGunTurn(true);
       while (true) {
           setTurnRadarRight(360);
           setTurnGunRight(360);
           execute();
       }
   }
   
   public void onScannedRobot(ScannedRobotEvent e) {
       	double kraft;
	double avstand = e.getDistance();
	double fart = e.getVelocity()+2;
	double retning = e.getHeading();
	double gunretning = getGunHeading();
	double vinkel = e.getBearing();
        
        double radarBearing = vinkel(vinkel + getHeading() - getRadarHeading()); //funker bra
        setTurnRadarRight (radarBearing);
        
        double gunBearing = vinkel(vinkel + getHeading() - getGunHeading());
        double vy = fart * Math.sin(radianer(radarBearing));
        double siktevinkel = grader(Math.asin(vy / kuleFart(3))) * avstand/10;
        setTurnGunRight(gunBearing + siktevinkel);
        System.out.println(siktevinkel);
        //setFire(3);
        setFire(3);
        execute();
//        double skytevinkel = 180 - gunBearing; // eller??
//        double snudd = vinkel(grader(Math.asin(Math.sin(radianer(vinkel(skytevinkel))) * fart / kuleFart(3)))); //sina*v2/v
//        turnGunRight(snudd);
//        execute();
//        setFire(3);
   }
   
   
    private double vinkel (double v) {
        if (v < -180) {
            return 360 + v;
        }
        if (v > 180) {
            return -(360 - v);
        }
        return v;
    }
    //gjoer om fra grader til radianer
    private double radianer(double vinkel) {
        return vinkel / 180 * Math.PI;
    }
    
     private double kuleFart(double kraft) {
	return 20 - (3 * kraft);
    }
    private double grader(double radian) {
        return radian * 180 / Math.PI;
    }
}
