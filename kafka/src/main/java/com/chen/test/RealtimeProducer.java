package com.chen.test;

import com.navinfo.stream.qingqi.protocol.java.RealTimeDataPb;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;
import java.util.Random;

/**
 * Created by chen on 2019/7/23.
 */
public class RealtimeProducer {
    public static String topic = "concentratedDataPB";

    public static void main(String[] args) throws InterruptedException {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "platform-010-030-050-179:9092");
//        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka167:16792");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        KafkaProducer<String, byte[]> kafkaProducer = new KafkaProducer<>(p);

        try {
            long time = 1563933891000L;
            RealTimeDataPb.RealTimeData.Builder pb =  RealTimeDataPb.RealTimeData.newBuilder();
            pb.setTerminalId(13800138000L);
            pb.setLatitude(110000000);
            pb.setLongitude(22000000);
            pb.setHeight(10);
            pb.setEngineOutputTorque(1);
            pb.setSpeed(90);
            pb.setAccelerator(50);
            pb.setBrake(20);
            pb.setRotation(800);
            pb.setGear(3);
            pb.setClutchSwitch(2);
            pb.setRealTimeOilConsumption(2);
            pb.setFuelConsumptionRate(3);
            pb.setRetransmissionPackets(4);
            pb.setReceiveDate(System.currentTimeMillis()/1000);
            pb.setOriginalLat(110);
            pb.setOriginalLng(22);
            pb.setProtocolType(1);
            pb.setVehicleSpeed(80);
            pb.setBarometricPressure(20);
            pb.setFrictionTorque(21);
            pb.setScrUpStreamNOxSensorOutput(22);
            pb.setScrDownStreamNOxSensorOutput(23);
            pb.setReagentAllowance(24);
            pb.setAirInflow(1);
            pb.setScrInletTemperature(2);
            pb.setScrOutletTemperature(3);
            pb.setDpfDropoutVoltage(4);
            pb.setEngineCoolantTemperature(5);
            pb.setTankLevel(6);
            pb.setPositioningState(7);
            pb.setMileage(8);
            pb.setRValue("RRRRRRRRR");
            pb.setSValue("SSSSSSSSs");
            pb.setCommandUnit(11);
            pb.setVin("vin123");
            pb.setTerminalVersion(12);
            pb.setEncryption(13);
            pb.setRLength(14);
            pb.setSLength(15);
            pb.setEngineBrakingState(16);
            pb.setExhaustBrakingState(17);
            pb.setEmptySignal(18);
            pb.setExhaustFlow(19);
            pb.setFanSpeed(20);
            pb.setMechanicalBoxGear(21);
            pb.setInstrumentSpeed(22);
            pb.setWheelSpeed(23);
            pb.setParkingBrakeSwitch(24);
            pb.setCruiseControlSettingSwitch(25);
            pb.setTargetGear(26);
            pb.setSlope(27);
            pb.setLoad(28);
            pb.setFuelLevel(29);
            pb.setWaterTemperature(30);
            pb.setAtmosphericPressure(31);
            pb.setIntakeAirTemperature(32);
            pb.setAtmosphericTemperature(33);
            pb.setExhaustGasTemperature(34);
            pb.setIntakeManifoldBoostPressure(35);
            pb.setRelativePressure(36);
            pb.setEngineTorqueMode(37);
            pb.setOilPressure(38);
            pb.setUreaLevel(39);
            pb.setStatusFlag(40);
            pb.setBrakeRate(41);
            pb.setGpsDirection(42);
            pb.setAirConditionStatus(43);
            pb.setTransmissionRotation(44);


            for(int i=0;i<10000;i++) {
                pb.setSerialNumber(i);
                pb.setGpsTime(time+i);

                ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(topic, pb.build().toByteArray());
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + i);
                Thread.sleep(10);
            }
        } finally {
            kafkaProducer.close();
        }

    }
}
