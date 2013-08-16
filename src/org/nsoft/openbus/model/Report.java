package org.nsoft.openbus.model;

public class Report {
	
	private double latitude;
	private double longitude;
	private String lineExpected;
	private int typeReport;
	private long hour;
	private long idReport;
	
	
	public long getIdReport(){
		return idReport;
	}
	
	public void setIdReport(long idReport){
		this.idReport = idReport;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getLineExpected() {
		return lineExpected;
	}
	
	public void setLineExpected(String lineExpected) {
		this.lineExpected = lineExpected;
	}
	
	public int getTypeReport() {
		return typeReport;
	}
	
	public void setTypeReport(int typeReport) {
		this.typeReport = typeReport;
	}
	
	public long getHour() {
		return hour;
	}
	
	public void setHour(long hour) {
		this.hour = hour;
	}
	
	
}
