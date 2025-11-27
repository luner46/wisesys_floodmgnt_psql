package kr.go.hrfco.api.me.vo;

import java.util.Objects;

/**
 * <pre>
 * @ClassName   : MeWlDataVO
 * @Description : 환경부 수위 관측소 데이터 VO
 *
 * -----------------------------------------------------
 * 2025.09.30, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.09.30
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

public class MeWlDataVO {
	
	private String realTime;
	private String yyyymmddhhmm;
	private String wlobscd;
	private String obsnm;
	private String agcnm;
	private String addr;
	private String etcaddr;
	private String lat;
	private String lon;
	private String pfg;
	private String gucd;
	private double wlOrgVal;
	private double fwOrgVal;
	private double wlSubVal;
	private double fwSubVal;
	private double wlVal;
	private double fwVal;
	
	public MeWlDataVO() {}
	
	public MeWlDataVO(String realTime, String yyyymmddhhmm, String wlobscd, String obsnm, String agcnm, String addr, String etcaddr, String lat, String lon, String pfg, String gucd, double wlOrgVal, double fwOrgVal, double wlSubVal, double fwSubVal, double wlVal, double fwVal) {
		this.realTime = realTime;
		this.yyyymmddhhmm = yyyymmddhhmm;
		this.wlobscd = wlobscd;
		this.obsnm = obsnm;
		this.agcnm = agcnm;
		this.addr = addr;
		this.etcaddr = etcaddr;
		this.lat = lat;
		this.lon = lon;
		this.pfg = pfg;
		this.gucd = gucd;
		this.wlOrgVal = wlOrgVal;
		this.fwOrgVal = fwOrgVal;
		this.wlSubVal = wlSubVal;
		this.fwSubVal = fwSubVal;
		this.wlVal = wlVal;
		this.fwVal = fwVal;
	}
	
	public String getRealTime() {return realTime;}
	
	public String getYyyymmddhhmm() {return yyyymmddhhmm;}
	
	public String getWlobscd() {return wlobscd;}
	
	public String getObsnm() {return obsnm;}
	
	public String getAgcnm() {return agcnm;}
	
	public String getAddr() {return addr;}
	
	public String getEtcAddr() {return etcaddr;}
	
	public String getLat() {return lat;}
	
	public String getLon() {return lon;}
	
	public String getPfg() {return pfg;}
	
	public String getGucd() {return gucd;}
	
	public double getWlOrgVal() {return wlOrgVal;}
	
	public double getFwOrgVal() {return fwOrgVal;}
	
	public double getWlSubVal() {return wlSubVal;}
	
	public double getFwSubVal() {return fwSubVal;}
	
	public double getWlVal() {return wlVal;}
	
	public double getFwVal() {return fwVal;}
	
	public void setRealTime(String realTime) {this.realTime = realTime;}
	
	public void setWlVal(double wlVal) {this.wlVal = wlVal;}
	
	public void setFwVal(double fwVal) {this.fwVal = fwVal;}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    MeWlDataVO commonMeWlDataVO = (MeWlDataVO) obj;
	    return realTime.equals(commonMeWlDataVO.realTime) && yyyymmddhhmm.equals(commonMeWlDataVO.yyyymmddhhmm) && wlobscd.equals(commonMeWlDataVO.wlobscd) && obsnm.equals(commonMeWlDataVO.obsnm) && agcnm.equals(commonMeWlDataVO.agcnm) && addr.equals(commonMeWlDataVO.addr) && etcaddr.equals(commonMeWlDataVO.etcaddr) && lat.equals(commonMeWlDataVO.lat) && lon.equals(commonMeWlDataVO.lon) && pfg.equals(commonMeWlDataVO.pfg) && gucd.equals(commonMeWlDataVO.gucd) && wlOrgVal == (commonMeWlDataVO.wlOrgVal) && fwOrgVal == (commonMeWlDataVO.fwOrgVal) && wlSubVal == (commonMeWlDataVO.wlSubVal) && fwSubVal == (commonMeWlDataVO.fwSubVal) && wlVal == (commonMeWlDataVO.wlVal) && fwVal == (commonMeWlDataVO.fwVal);
	}
    
    @Override
    public int hashCode() {
    	return Objects.hash(realTime, yyyymmddhhmm, wlobscd, obsnm, agcnm, addr, etcaddr, lat, lon, pfg, gucd, wlOrgVal, fwOrgVal, wlSubVal, fwSubVal, wlVal, fwVal);
    }
}
