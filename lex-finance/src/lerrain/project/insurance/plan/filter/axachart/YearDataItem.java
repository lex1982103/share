package lerrain.project.insurance.plan.filter.axachart;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yang
 * @date 2014-7-17 下午6:59:02
 */
public class YearDataItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String title;
	String desc;
	String mode;
	String code;
	BigDecimal[] data = new BigDecimal[200];

	public YearDataItem(String title, String desc, String mode) {
		this.title = title;
		this.desc = desc;
		this.mode = mode;
	}

	public void setItem(int index, BigDecimal bigDecimal) {
		data[index] = bigDecimal;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}

	public BigDecimal getItem(int index) {
		return data[index];
	}

	public double getItemDouble(int index) {
		return data[index] != null ? data[index].doubleValue() : -1;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public String getMode(){
		return mode;
	}
	
	public BigDecimal[] getData(){
		return data;
	}
}
