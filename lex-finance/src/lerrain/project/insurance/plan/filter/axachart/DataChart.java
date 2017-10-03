package lerrain.project.insurance.plan.filter.axachart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataChart implements Serializable {

	private static final long serialVersionUID = 1L;

	DataItem[] dataItems = new DataItem[80]; // 多条Item记录 List<DataItem>

	String code;
	int start;
	int end;
	int step;

	int spanSize;
	int size;

	List yearDataList = new ArrayList();
	Map additional;

	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
	
	public void addYearData(YearData yearData) {
		yearDataList.add(yearData);
	}

	public void setYearDataList(List list) {
		this.yearDataList = list;
	}

	public boolean hasYearData() {
		return yearDataList != null && !yearDataList.isEmpty();
	}

	public YearData getYearData(int index) {
		return (YearData) yearDataList.get(index);
	}

	public List getYearDataList() {
		return yearDataList;
	}

	public int getYearDataCount() {
		return yearDataList.size();
	}

	public void setItem(int lineIndex, int itemIndex, int start, int end, String type, String name, String color, BigDecimal bd) {
		if (dataItems[lineIndex] == null)
			dataItems[lineIndex] = new DataItem(name, type, color, start, end, itemIndex, bd);
		else
			dataItems[lineIndex].getData()[itemIndex] = bd;
	}

	public void setItem(int lineIndex, int itemIndex, String type, String name, String color, BigDecimal bd) {
		setItem(lineIndex, itemIndex, 0, 0, type, name, color, bd);
	}

	public DataItem getLine(int lineIndex) {
		return dataItems[lineIndex];
	}

	public BigDecimal getItem(int lineIndex, int itemIndex) {
		if (dataItems[lineIndex] != null)
			return dataItems[lineIndex].getData()[itemIndex];
		else
			return null;
	}

	public void setAdditional(String name, Object value) {
		if (additional == null)
			additional = new HashMap();

		additional.put(name, value);
	}

	public Object getAdditional(String name) {
		if (additional == null)
			return null;

		return additional.get(name);
	}

	public String getDesc() {
		return (String) additional.get("desc");
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getSpanSize() {
		return spanSize;
	}

	public void setSpanSize(int spanSize) {
		this.spanSize = spanSize;
	}

	public DataItem[] getDataItems() {
		return dataItems;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
