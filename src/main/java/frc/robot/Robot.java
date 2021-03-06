/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.lib.controllers.Xbox;
import frc.lib.util.CrashTracker;
import frc.robot.loops.Looper;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.Superstructure;


public class Robot extends TimedRobot {

  //Loopers
  private final Looper mEnabledLooper = new Looper();
  private final Looper mDisabledLooper = new Looper();

  //Drive team Controllers
  private final Xbox mDriveController = new Xbox(0);
  private final Xbox mOperatorController = new Xbox(1);
  

  //Subsystems
  private final SubsystemManager mSubsystemManager = SubsystemManager.getInstance();
  private final Superstructure mSuperstructure = Superstructure.getInstance();
  
  private final Shooter mShooter = Shooter.getInstance();



  Robot() {
    CrashTracker.logRobotConstruction();
  }


  @Override
  public void robotInit() {
    try {
      CrashTracker.logRobotInit();
    
      mSubsystemManager.setSubsystems(
        mSuperstructure

      );

      mSubsystemManager.registerEnabledLoops(mEnabledLooper);
      mSubsystemManager.registerDisabledLoops(mDisabledLooper);

      mSubsystemManager.zeroSensors();

    
      
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }


  @Override
  public void robotPeriodic() {
    try {
      mSubsystemManager.outputToSmartDashboard();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
    
  }

  @Override
  public void disabledInit() {
    try {
      CrashTracker.logDisabledInit();
      mEnabledLooper.stop();

      mDisabledLooper.start();


    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void disabledPeriodic() {
    try {

  

    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void autonomousInit() {
    try {
      CrashTracker.logAutoInit();
      mDisabledLooper.stop();

      //Zero sensors and robot state accordingly
      mSubsystemManager.zeroSensors();
      
      mEnabledLooper.start();


    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    try {

    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
  }

  @Override
  public void teleopInit() {
    try {
      mDisabledLooper.stop();

      mEnabledLooper.start();
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  private double localShooterPower = 0.0;
  private String controlMode = "dPad";

  @Override
  public void teleopPeriodic() {
    try {
      mDriveController.update();
      mOperatorController.update();
      driverControl();
    
    } catch (Throwable t) {
      CrashTracker.logThrowableCrash(t);
      throw t;
    }
    /*
    if(Xbox.dpadDown.wasReleased() && Xbox.dpadUp.wasReleased()){
      if (Xbox.dpadDown.equals(currentButton)){
        localShooterPower -= 0.1;
      } else {
        localShooterPower += 0.1;
      }
    }
    
    //two different control types, one using trigger, other with increments on dPad.
    if(mDriveController.aButton.wasReleased()){
      controlMode = "dPad";
      System.out.println(controlMode);
    } else if(mDriveController.bButton.wasReleased()){
      controlMode = "Axis";
      System.out.println(controlMode);
    }
    
    switch(controlMode){
      case ("dPad"):
        dPadControl();
        break;
      case ("Axis"):
        localShooterPower = mDriveController.getTriggerAxis(Hand.kRight);
    }
    // code for trigger controlled power w/ out different modes. Trigger takes precedent and over rules dPad completely.
    dPadControl();
    if(mDriveController.rightTrigger.isBeingPressed()){
      localShooterPower = mDriveController.getTriggerAxis(Hand.kRight);
    }
    */
    if(mDriveController.aButton.wasReleased()){
      localShooterPower = -1;
    }
    dPadControl();
    mShooter.shoot(localShooterPower);
    //mShooter.updateTelemetry();
  }

  private void dPadControl(){
    if(mDriveController.dpadDown.wasReleased() && localShooterPower <= 0.9){
      localShooterPower += 0.1;
      System.out.println(localShooterPower);
    } else if (mDriveController.dpadUp.wasReleased() && localShooterPower >= -0.9){
      localShooterPower -= 0.1;
      System.out.println(localShooterPower);
    } else if (mDriveController.dpadRight.wasReleased() && localShooterPower >= -0.95){
      localShooterPower -= 0.05;
      System.out.println(localShooterPower);
    } else if (mDriveController.dpadLeft.wasReleased() && localShooterPower <= 0.95){
      localShooterPower += 0.05;
      System.out.println(localShooterPower);
    } else if (mDriveController.xButton.wasReleased()){
      localShooterPower = 0.0;
      System.out.println(localShooterPower);
    } else if(mDriveController.yButton.longPressed() && localShooterPower < 1){
      localShooterPower *= -1;
      System.out.println(localShooterPower);
    }
  }

  @Override
  public void testInit() {
    try {
      CrashTracker.logTestInit();
      mDisabledLooper.stop();

      mEnabledLooper.start();
      
      
    } catch(Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  @Override
  public void testPeriodic() { 
    try {

      
    } catch(Throwable t) {
      CrashTracker.logThrowableCrash(t);
    }
  }

  

  public void driverControl() {
    
  }

 
}

