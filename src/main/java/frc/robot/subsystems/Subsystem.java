/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.loops.ILooper;
import java.util.HashMap;
/**
 * Add your docs here.
 */
public abstract class Subsystem {

    public void writeToLog() {}

    public void readPeriodicInputs() {}

    public void writePeriodicOutputs() {}

    public void registerEnabledLoops(ILooper mEnabledLooper) {}

    public void zeroSensors() {}

    public abstract void stop();

    public abstract boolean checkSystem();

    public abstract void updateTelemetry();
    
    public HashMap<String, Object> outputTelemetry = new HashMap<>();

    public final void outputTelemetry() {
        Dashboard.parse(outputTelemetry, getName());
    }

    public abstract String getName();

    public boolean hasEmergency = false;
}
