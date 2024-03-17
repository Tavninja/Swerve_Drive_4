// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.SwerveModule;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    
    final XboxController manipulatorJoystick = new XboxController(1);

    double ShootSpeed = 1;
    double intakeSpeed = .7;
    double LiftSpeed = .35;

    CANSparkMax IntakeTop = new CANSparkMax(11, MotorType.kBrushless);
    CANSparkMax IntakeBottom = new CANSparkMax(12, MotorType.kBrushless);
    TalonFX LiftLeft = new TalonFX(13);
    TalonFX LiftRight = new TalonFX(14);
    TalonFX ShootLeft = new TalonFX(15);
    TalonFX ShootRight = new TalonFX(16);


    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer. This will perform all our button bindings,
        // and put our
        // autonomous chooser on the dashboard.
        m_robotContainer = new RobotContainer();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and
     * test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        


        //Shoot -----------------------------------------------------
        if(manipulatorJoystick.getRightTriggerAxis() >=.5){
            ShootLeft.set(ShootSpeed);
            ShootRight.set(-ShootSpeed);
        } else if(manipulatorJoystick.getLeftTriggerAxis() >= .5) {
            ShootLeft.set(-ShootSpeed);
            ShootRight.set(ShootSpeed);
        }else {
            ShootLeft.set(0);
            ShootRight.set(0);
        }
        //Lift -------------------------------------------------------
        if(manipulatorJoystick.getRightBumper()){
            LiftLeft.set(LiftSpeed);
            LiftRight.set(-LiftSpeed); 
        } else if (manipulatorJoystick.getLeftBumper()){
            LiftLeft.set(-LiftSpeed);
            LiftRight.set(LiftSpeed);
        } else {
            LiftLeft.set(0);
            LiftRight.set(0);
        }
        //Intake ------------------------------------------------------
        if(manipulatorJoystick.getAButton()){
            IntakeBottom.set(intakeSpeed);
            IntakeTop.set(-intakeSpeed);
        } else if(manipulatorJoystick.getBButton()){
            IntakeBottom.set(-intakeSpeed);
            IntakeTop.set(intakeSpeed);
        } else{
            IntakeBottom.set(0);
            IntakeTop.set(0);
        }
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
