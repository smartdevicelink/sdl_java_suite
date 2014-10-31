package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.ClusterModeStatus;
import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.ECallInfo;
import com.smartdevicelink.proxy.rpc.EmergencyEvent;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.TireStatus;


public enum VehicleData{
    GPS("GPS", "gps", GPSData.class),
    SPEED("Speed", "speed", Double.class),
    RPM("RPM", "rpm", Integer.class),
    FUEL_LEVEL("Fuel Level", "fuelLevel", Double.class),
    FUEL_LEVEL_STATE("Fuel Level State", "fuelLevel_State", ComponentVolumeStatus.class),
    INSTANT_FUEL_CONSUMPTION("Instant Fuel Consumption", "instantFuelConsumption", Double.class),
    EXTERNAL_TEMPERATURE("External Temperature", "externalTemperature", Double.class),
    VIN("VIN", "vin", String.class),
    PRNDL("PRNDL", "prndl", PRNDL.class),
    TIRE_PRESSURE("Tire Pressure", "tirePressure", TireStatus.class),
    ODOMETER("Odometer", "odometer", Integer.class),
    BELT_STATUS("Belt Status", "beltStatus", BeltStatus.class),
    BODY_INFORMATION("Body Information", "bodyInformation", BodyInformation.class),
    DEVICE_STATUS("Device Status", "deviceStatus", DeviceStatus.class),
    DRIVER_BRAKING("Driver Braking", "driverBraking", VehicleDataEventStatus.class),
    WIPER_STATUS("Wiper Status", "wiperStatus", WiperStatus.class),
    HEAD_LAMP_STATUS("Head Lamp Status", "headLampStatus", HeadLampStatus.class),
    ENGINE_TORQUE("Engine Torque", "engineTorque", Double.class),
    ACC_PEDAL_POSITION("Accelerator Pedal Position", "accPedalPosition", Double.class),

    // NEW VEHICLE DATA FOR GEN 3
    STEERING_WHEEL_ANGLE("Steering Wheel Angle", "steeringWheelAngle", Double.class),
    E_CALL_INFO("E-Call Info", "eCallInfo", ECallInfo.class),
    AIRBAG_STATUS("Airbag Status", "airbagStatus", AirbagStatus.class),
    EMERGENCY_EVENT("Emergency Event", "emergencyEvent", EmergencyEvent.class),
    CLUSTER_MODE_STATUS("Cluster Mode Status", "clusterModeStatus", ClusterModeStatus.class),
    MY_KEY("My Key", "myKey", MyKey.class),

    // future vehicle data goes here
    ;

    private final String   friendlyName, jsonName;
    private final Class<?> classType;

    private VehicleData(String friendlyName, String jsonName, Class<?> classType){
        this.friendlyName = friendlyName;
        this.jsonName = jsonName;
        this.classType = classType;
    }

    @Override
    public String toString(){
        return friendlyName;
    }

    public Class<?> getClassType(){
        return this.classType;
    }

    public String getJsonName(){
        return jsonName;
    }

    public static VehicleData valueForJsonName(String jsonName){
        if(jsonName == null) return null;

        for(VehicleData anEnum : EnumSet.allOf(VehicleData.class)){
            if(anEnum.getJsonName().equals(jsonName)){
                return anEnum;
            }
        }
        return null;
    }
}
