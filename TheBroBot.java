/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author K
 */
import robocode.*;

public class TheBroBot extends AdvancedRobot {
    
        private boolean retning = false;
        private double bommetPaaRad = 0; // i energi ikke kuler
        private String target;
        
        public void run() {
            
            
            setAdjustGunForRobotTurn(false);
           
            
            while (true) {
              setAhead(500);
              snu();
              execute();
            }
        }
        
        public void onScannedRobot(ScannedRobotEvent e) {
            
            target = e.getName();
            
            double kraft;
            double avstand = e.getDistance();
            double fart = e.getVelocity();
            double retning = e.getHeading();
            double gunretning = getGunHeading();
            //setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
            double vinkel = e.getBearing();
            if (vinkel < 0) {
                this.retning = true; // venstre
            }
            else {
                this.retning = false;
            }
            if (avstand > 50) {
                setTurnRight(vinkel/5);
                setAhead(avstand/10);
            }
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
                setTurnLeft(360);
            }
            else {
                setTurnRight(360);
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
        
        
        
}
