
import robocode.*;

public class TheBroBot extends AdvancedRobot {
    
    private boolean retning = false;
    private double bommetPaaRad = 0; // i energi ikke kuler
    private String target;
    private boolean leder;  //maalt i energi
    private boolean chickenmode;
    private double targetEnergy;
    
    
    public void run() {
	
        
        
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
	while (true) {

		setTurnGunRight(getHeading() - getGunHeading());
	    if (getEnergy() < 35 && !leder)  {
		chickenmode();
	    }
	    setAhead(500);
            setTurnRadarRightc(360);
	    snu();
	    execute();
	}
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {

	if (e.getEnergy() < getEnergy()) {
	    leder = true;
	}
	else if (e.getEnergy() > getEnergy() +5) {
	    leder = false;
	}
	
	target = e.getName();
        targetEnergy = e.getEnergy();
        
	double kraft;
	double avstand = e.getDistance();
	double fart = e.getVelocity();
	double retning = e.getHeading();
	double gunretning = getGunHeading();
	double vinkel = e.getBearing();
        
        
        double radarBearing = vinkel(vinkel + getHeading() - getRadarHeading()); //funker bra
        setTurnRadarRight (radarBearing);
        
        
	if (vinkel < 0) {
	    this.retning = true; // venstre
	}
	else {
	    this.retning = false;
	}
	
	if (chickenmode) {
                if (avstand < 100) {
                    setBack(500);
                    setTurnRight(90);
                    execute();
                }
                double gunBearing = vinkel(vinkel + getHeading() - getGunHeading());
                setTurnGunRight(-getGunTurnRemaining() + gunBearing);
                setTurnRight (vinkel(-getTurnRemaining() + vinkel + 90)); //kjøre rundt??
                setAhead(avstand);
                execute();
		return;
	}
        
        double gunBearing = vinkel(vinkel + getHeading() - getGunHeading());
        double vy = fart * Math.sin(radianer(radarBearing));
        double siktevinkel = grader(Math.asin(vy / kuleFart(3))) * avstand/10;
        setTurnGunRight(gunBearing + siktevinkel);
        execute();
	if (avstand > 50) {
	    setAhead(avstand/10);
	}
        setTurnRight(vinkel/3);
	if (fart == 0 || rettMotMeg(retning)) {
	    fire(3);
	}
	else {
	    setFire(0.5 + 200/avstand);
	}
        
    }
    
    //            if (vinkel < 0) {
    //                setTurnLeftRadians(Math.asin(Math.sin(Math.PI + vinkel) * fart / kuleFart(3)));
    //                this.retning = true;
    //            }
    //            else {
    //                setTurnRightRadians(Math.asin(Math.sin(Math.PI - vinkel) * fart / kuleFart(3)));
    //                this.retning = false;
    //            }
    //            execute();
    //            fire(3);
    //           
    //        }
    
    private double kuleFart(double kraft) {
	return 20 - (3 * kraft);
    }
    
    public void onBulletMissed(BulletMissedEvent e) {
	
        bommetPaaRad += e.getBullet().getPower();
    }
    
    
    private void snu() {
	if (retning) {
	    setTurnLeft(100);
	}
	else {
	    setTurnRight(100);
	}
        
    }
    private boolean rettMotMeg(double retning) {
	double differanse = getHeading() - retning;
        
	if (differanse < -175 && differanse > -185) {
	    return true;
	}
	if (differanse > 175 && differanse < 185) {
	    return true;
	}
	if (differanse > -5 && differanse < 5) {
	    return true;
	}
	return false;
    }
    
    public void onDeath(DeathEvent e) {
	System.out.println("Bortkastet energi: " + bommetPaaRad);
    }
    public void onWin(WinEvent e) {          
	System.out.println("Bortkastet energi: " + bommetPaaRad);
    }
    
    private void chickenmode() {
        
	stop();
        setAdjustGunForRobotTurn(true);
	while (!leder) { 
	    chickenmode = true;
            setTurnGunRight(360);
	    setAhead(200);
	    setTurnRight(180);
	    setBack(100);
	    setTurnLeft(720);
	    execute();
			
	}
	chickenmode = false;
	    
    
    }
    private void snuTaarn(double vinkel) {
        if (vinkel < 0) {
	    setTurnGunLeft(1080);
	}
	else {
	    setTurnGunRight(1080);
	}
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
    
    public void onHitWall(HitWallEvent e) {
        setTurnRight(e.getBearing() - 180);
        setAhead(400);
    }
    private double grader(double radian) {
        return radian * 180 / Math.PI;
    }
     private double radianer(double vinkel) {
        return vinkel / 180 * Math.PI;
    }
}
    


