package kr.go.hrfco.main.vo;

import java.util.Objects;

public class LinkDataVO {
	private String name;
	private String smh;
	private String emh;
	private double length;
	private double slope;
	private double startX;
	private double startY;
	private double centerX;
	private double centerY;
	private double angleDeg;
	private double area;
	private double width;
	private double grdLev;
	private double degreeX;
	private double degreeY;

	public LinkDataVO() {}
	
	public LinkDataVO(String name, String smh, String emh, double length, double slope, double startX, double startY, double centerX, double centerY, double angleDeg, double area, double width, double grdLev, double degreeX, double degreeY) {
		this.name = name;
		this.smh = smh;
		this.emh = emh;
		this.length = length;
		this.slope = slope;
		this.startX = startX;
		this.startY = startY;
		this.centerX = centerX;
		this.centerY = centerY;
		this.angleDeg = angleDeg;
		this.area = area;
		this.width = width;
		this.grdLev = grdLev;
		this.degreeX = degreeX;
		this.degreeY = degreeY;
	}
	
	public String getName() {return name;}

	public String getSmh() {return smh;}

	public String getEmh() {return emh;}

	public double getLength() {return length;}

	public double getSlope() {return slope;}

	public double getStartX() {return startX;}

	public double getStartY() {return startY;}

	public double getCenterX() {return centerX;}

	public double getCenterY() {return centerY;}

	public double getAngleDeg() {return angleDeg;}
	
	public double getArea() {return area;}
	
	public double getWidth() {return width;}
	
	public double getGrdLev() {return grdLev;}
	
	public double getDegreeX() {return degreeX;}
	
	public double getDegreeY() {return degreeY;}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LinkDataVO commonLinkDataVO = (LinkDataVO) obj;
        return name.equals(commonLinkDataVO.name) && smh.equals(commonLinkDataVO.smh) && emh.equals(commonLinkDataVO.emh) && length == (commonLinkDataVO.length) && slope == (commonLinkDataVO.slope)&& startX == (commonLinkDataVO.startX) && startY == (commonLinkDataVO.startY)  && centerX == (commonLinkDataVO.centerX) && centerY == (commonLinkDataVO.centerY) && angleDeg == (commonLinkDataVO.angleDeg) && area == (commonLinkDataVO.area) && width == (commonLinkDataVO.width) && grdLev == (commonLinkDataVO.grdLev) && degreeX == (commonLinkDataVO.degreeX) && degreeY == (commonLinkDataVO.degreeY);
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(name, smh, emh, length, slope, startX, startY, centerX, centerY, angleDeg, area, width, grdLev, degreeX, degreeY);
    }
}
