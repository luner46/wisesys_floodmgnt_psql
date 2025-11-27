package kr.go.hrfco.api.me.vo;

import java.util.Objects;

/**
 * <pre>
 * @ClassName   : UffBsceDataVO
 * @Description : Uff Bsce 데이터 VO
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

public class UffBsceDataVO {
	
	private String realTime;
	private String initTm;
	private String bsn;
	private String orgnlRmse;
	private String subRmse;
	private String orgnlWl;
	private String subWl;
	private String orgnlRn;
	private String subRn;
	private String orgnlDur;
	private String subDur;
	private String orgnlOx;
	private String subOx;
	private String rmseVal;
	private String wlVal;
	private String rnVal;
	private String durVal;
	private String oxVal;
	
	public UffBsceDataVO() {}
	
	public UffBsceDataVO(String realTime, String initTm, String bsn, String orgnlRmse, String subRmse, String orgnlWl, String subWl, String orgnlRn, String subRn, String orgnlDur, String subDur, String orgnlOx, String subOx, String rmseVal, String wlVal, String rnVal, String durVal, String oxVal) {
		this.realTime = realTime;
		this.initTm = initTm;
		this.bsn = bsn;
		this.orgnlRmse = orgnlRmse;
		this.subRmse = subRmse;
		this.orgnlWl = orgnlWl;
		this.subWl = subWl;
		this.orgnlRn = orgnlRn;
		this.subRn = subRn;
		this.orgnlDur = orgnlDur;
		this.subDur = subDur;
		this.orgnlOx = orgnlOx;
		this.subOx = subOx;
		this.rmseVal = rmseVal;
		this.wlVal = wlVal;
		this.rnVal = rnVal;
		this.durVal = durVal;
		this.oxVal = oxVal;
	}
	
	public String getRealTime() {return realTime;}
	
	public String getInitTm() {return initTm;}
	
	public String getBsn() {return bsn;}
	
	public String getOrgnlRmse() {return orgnlRmse;}
	
	public String getSubRmse() {return subRmse;}
	
	public String getOrgnlWl() {return orgnlWl;}
	
	public String getSubWl() {return subWl;}
	
	public String getOrgnlRn() {return orgnlRn;}
	
	public String getSubRn() {return subRn;}
	
	public String getOrgnlDur() {return orgnlDur;}
	
	public String getSubDur() {return subDur;}
	
	public String getOrgnlOx() {return orgnlOx;}
	
	public String getSubOx() {return subOx;}
	
	public String getRmseVal() {return rmseVal;}
	
	public String getWlVal() {return wlVal;}
	
	public String getRnVal() {return rnVal;}
	
	public String getDurVal() {return durVal;}
	
	public String getOxVal() {return oxVal;}
	
	public void setRealTime(String realTime) {this.realTime = realTime;}

	public void setRmseVal(String rmseVal) {this.rmseVal = rmseVal;}
	
	public void setWlVal(String wlVal) {this.wlVal = wlVal;}
	
	public void setRnVal(String rnVal) {this.rnVal = rnVal;}
	
	public void setDurVal(String durVal) {this.durVal = durVal;}
	
	public void setOxVal(String oxVal) {this.oxVal = oxVal;}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    UffBsceDataVO commonBsceVO = (UffBsceDataVO) obj;
	    return realTime.equals(commonBsceVO.realTime) && initTm.equals(commonBsceVO.initTm) && bsn.equals(commonBsceVO.bsn) && orgnlRmse.equals(commonBsceVO.orgnlRmse) && subRmse.equals(commonBsceVO.subRmse) && orgnlWl.equals(commonBsceVO.orgnlWl) && subWl.equals(commonBsceVO.subWl) && orgnlRn.equals(commonBsceVO.orgnlRn) && subRn.equals(commonBsceVO.subRn) && orgnlDur.equals(commonBsceVO.orgnlDur) && subDur.equals(commonBsceVO.subDur) && orgnlOx.equals(commonBsceVO.orgnlOx) && subOx.equals(commonBsceVO.subOx) && rmseVal.equals(commonBsceVO.rmseVal) && wlVal.equals(commonBsceVO.wlVal) && rnVal.equals(commonBsceVO.rnVal) && durVal.equals(commonBsceVO.durVal) && oxVal.equals(commonBsceVO.oxVal);
	}
    
    @Override
    public int hashCode() {
    	return Objects.hash(realTime, initTm, bsn, orgnlRmse, subRmse, orgnlWl, subWl, orgnlRn, subRn, orgnlDur, subDur, orgnlOx, subOx, rmseVal, wlVal, rnVal, durVal, oxVal);
    }
}
