// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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

    double ShootSpeed = .8;
    double intakeSpeed = .5;
    double LiftSpeed = .35;

    CANSparkMax IntakeTop = new CANSparkMax(11, MotorType.kBrushless);
    CANSparkMax IntakeBottom = new CANSparkMax(12, MotorType.kBrushless);
    
    static CANSparkMax frontLeftDrive = new CANSparkMax(5, MotorType.kBrushless);
    static CANSparkMax backLeftDrive = new CANSparkMax(7, MotorType.kBrushless);
    static CANSparkMax frontRightDrive = new CANSparkMax(6, MotorType.kBrushless);
    static CANSparkMax backRightDrive = new CANSparkMax(5, MotorType.kBrushless);

    static CANSparkMax frontLeftTurning = new CANSparkMax(1, MotorType.kBrushless);
    static CANSparkMax backLeftTurning = new CANSparkMax(3, MotorType.kBrushless);
    static CANSparkMax frontRightTurning = new CANSparkMax(2, MotorType.kBrushless);
    static CANSparkMax BackRightTurning = new CANSparkMax(4, MotorType.kBrushless);

    CANSparkMax CA

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

        // Auto Switcher ----------------------------------------------------
   m_chooser.setDefaultOption("Stay Still", Stay_Still);
   m_chooser.addOption("Backward", Backward);
   m_chooser.addOption("Shoot_Back_Short", Shoot_Back);
   m_chooser.addOption("Shoot_Back_Long", Shoot_Still);

   SmartDashboard.putData("Auto choices", m_chooser);
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

    public double getTurningPosition() {
        return turningEncoder.getPosition();
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */

      // Auto Time Contants
        private Timer timer;
        private double step1Time = 1;             //
        private double step2Time = step1Time + 1; //
        private double step3Time = step2Time + .5;//
        private double step4Time = step3Time + 1; //
        private double step5Time = step4Time + 1; //
        private double step6Time = step5Time + 1; //
        private static final String Stay_Still = "Still";
        private static final String Backward = "Backward";
        private static final String Shoot_Back = "Shoot_Backwards";
        private static final String Shoot_Still = "Shoot_Still";


    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

// Movement functions ------------------------------------------------

    static void Forward(){
     frontLeftDrive.set(.5);
     backLeftDrive.set(.5);
     frontRightDrive.set(.5);
     backRightDrive.set(.5);

     frontLeftDrive.getPosition();

    }   
    static void Backwards(){
     frontLeftDrive.set(.5);
     backLeftDrive.set(.5);
     frontRightDrive.set(.5);
     backRightDrive.set(.5);
    }
    static void Left(){
     frontLeftDrive.set(.5);
     backLeftDrive.set(.5);
     frontRightDrive.set(.5);
     backRightDrive.set(.5);
    }
    static void Right(){
     frontLeftDrive.set(.5);
     backLeftDrive.set(.5);
     frontRightDrive.set(.5);
     backRightDrive.set(.5);
    }
    static void Stoped(){
     frontLeftDrive.set(0);
     backLeftDrive.set(0);
     frontRightDrive.set(0);
     backRightDrive.set(0);
    }
    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);
     
        timer = new Timer();
        timer.start();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        switch (m_autoSelected) {
            case Backward:
            if (timer.get() <= step1Time) {
             Backwards();
           } else if (timer.get() <= step2Time) {
             Backwards();
           } else if (timer.get() <= step3Time) {
             Stoped();
           }else if (timer.get() <= step4Time) {
             Stoped();
           } else if (timer.get() <= step5Time) {
             Stoped();
           } else if (timer.get() <= step6Time){
             Stoped();
           } else {
             Stoped();
           }
             break;

           case Stay_Still: //--------------------------Red 1
           if (timer.get() <= step1Time) {
            Backwards();
          } else if (timer.get() <= step2Time) {
            Backwards();
          } else if (timer.get() <= step3Time) {
            Stoped();
          }else if (timer.get() <= step4Time) {
            Stoped();
          } else if (timer.get() <= step5Time) {
            Stoped();
          } else if (timer.get() <= step6Time){
            Stoped();
          } else {
            Stoped();
          }
            break;
        }
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
