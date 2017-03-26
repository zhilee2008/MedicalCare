package com.ysqm.medicalcare.utils;

import com.ysqm.medicalcare.cra.CRA_ReportProjectMenuHospital1;


public class Constants {
	
	public static String WSDL_SERVER_LIST = "http://103.248.103.12:8888/conf/configuration.json";
	
//	public static String HOST="";
//	public static String PORT="";
//	
//		
//	public static String WSDL_URL_TRACK = "http://"+HOST+":"+PORT+"/track/test/";
//	public static String WSDL_URL = "http://"+HOST+":"+PORT+"/track/service/common/";
//	public static String WSDL_URL_REPORT = "http://"+HOST+":"+PORT+"/track/report/";
	public static String WSDL_URL_TRACK = "http://103.248.103.12:8080/track/test/";
	public static String WSDL_URL = "http://103.248.103.12:8080/track/service/common/";
	public static String WSDL_URL_REPORT = "http://103.248.103.12:8080/track/report/";
	
	public static String PATIENT="patient";
	public static String CRC="crc";
	public static String CRA="cra";
	public static String DOCTOR="doctor";
	public static String CRALEAD="cralead";
	
	public static int SUCCESSCODE=400;
	
	public static String CHECKUP="checkup";
	
	public static String P_SUBMITORDER="checkup/order";
	
	public static String STATE="?state=";

	public static String P_CURRENTORDER="checkup?state=";
	
	public static String CHECKUPID="?checkupId=";
	public static String PATIENTID="&patientId=";
	
//	public static String P_CURRENTORDER_STATE="confirmed";
	public static String P_ORDERING_STATE="ordering|opening|confirmed|rejected|expired";
	
	public static String P_CURRENTORDER_STATE="ordering|opening|rejected";
	public static String P_CURRENTORDERED_STATE="ordered";
	public static String P_CURRENTCONFIRM_STATE="confirmed";
	public static String P_CURRENTCHECKED_STATE="checked";
	
	public static String P_ORDEREDANDCONFIRMED_STATE="ordered|confirmed|checked";
	
	public static String P_HISTORYORDER_STATE="complete";
	
	public static String CRC_ORDERED_STATE="ordered";
	public static String CRC_CHECKED_STATE="checked";
	public static String CRC_CONFIRMED_STATE="confirmed";
	public static String CRC_COMPLETE_STATE="complete";
	
	public static String CRC_CHECKUPCONFIRM="checkup/confirm";
	public static String CRC_CHECKUPCHECKED="checkup/checked";
	public static String CRC_CHECKUPCOMPLETE="checkup/complete";
	
	public static String CRCPROJECT="filterproject";
	public static String CRCHOSPITAL="filterhospital";
	public static String CRC_REJECT="checkup/reject";
	
	public static String CRAPROJECT="filterproject";
	public static String CRAPROJECTHOSPITAL="filterhospital?projectId=";
	public static String CRAPROJECTCRC="filteruser?role=crc&projectId=";
	public static String CRACRC="crc?reportManagerUserId=";
	public static String CRALEADCRA="cra?reportManagerUserId=";
	public static String CRALEADCRC="crc?cralead=";
	
	public static String CRC_ADDPATIENT="createPatientAndPlan.html";	
	
	//http://103.248.103.12:8080/track/report/patientStateStatistic.jsp
	public static String CRA_REPORTPROJECTALL1="patientStateStatistic.jsp";
	//http://localhost:8080/track/report/checkupStateStatistic.jsp?projectId=11&hospitalId=11&crcId=crc_ab1
	public static String CRA_REPORTPROJECTALL2="checkupStateStatistic.jsp";
	//http://localhost:8080/track/report/checkItemStatistic.jsp?projectId=11&sequence=1
	public static String CRA_REPORTPROJECTALL3="checkItemStatistic.jsp";
	//http://localhost:8080/track/report/patientStateStatistic.jsp?projectId=11&hosptialId=11
	public static String CRA_REPORTPROJECTMENUHOSPITAL1="patientStateStatistic.jsp";
	//http://localhost:8080/track/report/checkupStateStatistic.jsp?projectId=11&hospitalId=11&crcId=crc_ab1
	public static String CRA_REPORTPROJECTMENUHOSPITAL2="checkupStateStatistic.jsp";
	//http://localhost:8080/track/report/patientStateStatistic.jsp?projectId=11&crcId=crc_ab1
	public static String CRA_REPORTPROJECTMENUCRC1="patientStateStatistic.jsp";	 
	//http://localhost:8080/track/report/checkupStateStatistic.jsp?projectId=11&crcId=crc_ab1
	public static String CRA_REPORTPROJECTMENUCRC2="checkupStateStatistic.jsp";
	//http://localhost:8080/track/report/patientStateStatistic.jsp?crcId=crc_ab1
	public static String CRA_REPORTCRCMENUCRC1="patientStateStatistic.jsp";	 	 
	//http://localhost:8080/track/report/patientStateStatisticBar.jsp?crcId=crc_ab1
	public static String CRA_REPORTCRCMENUCRC2="patientStateStatisticBar.jsp";	 
	
	//http://localhost:8080/track/report/patientStateStatistic.jsp?craId=cra_ab1
	public static String CRALEAD_REPORTCRAMENUCRA1="patientStateStatistic.jsp";
	//http://localhost:8080/track/report/patientStateStatisticBar.jsp?crcId=crc_ab1
	public static String CRALEAD_REPORTCRAMENUCRA2="patientStateStatisticBar.jsp";
	
	public static String LOGIN = "login";
	
	public static String PLAN ="plan";
	
	public static String CHANGEPWD = "changpassword";
	
	public static String ADDPATIENT = "patient";
	
	public static String CHECKITEM="checkitem";
	
	

	
	public enum Configuration {
		name, 
		port,
		host,
	}
	
	
	
}
