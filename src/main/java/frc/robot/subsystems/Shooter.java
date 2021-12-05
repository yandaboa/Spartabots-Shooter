package frc.robot.subsystems;
import java.util.ArrayList;
import frc.lib.controllers.Xbox;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.drivers.LazyTalonSRX;
import frc.lib.drivers.MotorChecker;
import frc.lib.drivers.PheonixUtil;
import frc.lib.drivers.TalonFXChecker;
import frc.lib.drivers.TalonSRXFactory;
import frc.lib.drivers.TalonSRXUtil;
import frc.lib.util.CircularBuffer;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;
import frc.robot.subsystems.requests.Request;
import edu.wpi.first.wpilibj.shuffleboard.*;


public class Shooter extends Subsystem{
    
    private static Shooter mInstance = null;

    public static Shooter getInstance() {
        if(mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }
    private final LazyTalonSRX mShooterMotor;


    private void configureShooterMotor (LazyTalonSRX talon, InvertType inversion){
        talon.setInverted(inversion);
        PheonixUtil.checkError(talon.configVoltageCompSaturation(12.0, Constants.kTimeOutMs),
            talon.getName() + " failed to set voltage compensation", true);
        talon.enableVoltageCompensation(true);
        talon.setNeutralMode(NeutralMode.Coast);
        TalonSRXUtil.setCurrentLimit(talon, 25);
    }

    private Shooter(){
        mShooterMotor = TalonSRXFactory.createDefaultTalon("Shooter Motor", Ports.SHOOTER_ID);
        configureShooterMotor(mShooterMotor, InvertType.None);

    }

    private double localShooterPower;
    // we changed the Xbox enums to static because we don't know any better.
    public void shoot(double localShooterPower) {
        this.localShooterPower = localShooterPower;
        mShooterMotor.set(ControlMode.PercentOutput, localShooterPower);
    }

    public String getName(){
        return "Shooter";
    }
    
    public void writeToLog() {

    }

    public void readPeriodicInputs() {

    }

    public void writePeriodicOutputs() {

    }

    public void registerEnabledLoops(ILooper mEnabledLooper) {

    }

    public void zeroSensors() {

    }
    
    @Override
    public void stop(){

    }

    @Override
    public boolean checkSystem(){
        return false;
    }

    @Override
    public void updateTelemetry(){
        outputTelemetry.put("Current Power Percentage", localShooterPower*100);
        outputTelemetry();
    }

    public boolean hasEmergency = false;
}
