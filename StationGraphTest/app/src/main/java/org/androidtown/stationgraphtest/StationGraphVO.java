package org.androidtown.stationgraphtest;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StationGraphVO implements Comparable<StationGraphVO>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public String getStationName() {
		return stationName;
	}

	public String getLineNum() {
		return lineNum;
	}

	public void setMainLine(boolean mainLine) {
		this.mainLine = mainLine;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public enum Identifier {
		PREVIOUS, CURRENT, NEXT
	}
	
	private String stationName;
	private String lineNum;
	private Identifier identifier;
	private boolean isMainLine = true;
	
	public StationGraphVO(String stationName, String lineNum, Identifier identifier) {
		super();
		this.stationName = stationName;
		this.lineNum = lineNum;
		this.identifier = identifier;
	}

	@Override
	public int compareTo(StationGraphVO o) {
		return this.stationName.compareTo(o.stationName);
	}
}