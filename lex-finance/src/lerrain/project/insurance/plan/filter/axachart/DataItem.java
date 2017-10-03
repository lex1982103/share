package lerrain.project.insurance.plan.filter.axachart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataItem implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_LINE = 1;
	public static final int TYPE_BAR = 2;
	public static final int TYPE_NULL = 3;

	BigDecimal[] data = new BigDecimal[200]; // 对应数据

	int start;
	int end;
	int type;

	String name;
	String color;

	public DataItem() {
		type = TYPE_LINE;
	}

	public DataItem(String name) {
		this.name = name;
		type = TYPE_LINE;
	}

	public DataItem(String name, String type) {
		this.name = name;
		this.type = "line".equalsIgnoreCase(type) ? TYPE_LINE : "bar".equalsIgnoreCase(type) ? TYPE_BAR : TYPE_LINE;
	}

	public DataItem(String name, String type, String color) {
		this.name = name;
		this.type = "line".equalsIgnoreCase(type) ? TYPE_LINE : "bar".equalsIgnoreCase(type) ? TYPE_BAR : TYPE_LINE;
		this.color = color;
	}

	public DataItem(String name, String type, String color, int start, int end) {
		this.name = name;
		this.type = "line".equalsIgnoreCase(type) ? TYPE_LINE : "bar".equalsIgnoreCase(type) ? TYPE_BAR : TYPE_LINE;
		this.color = color;
		this.start = start;
		this.end = end;
	}

	public DataItem(String name, String type, String color, int start, int end, int itemIndex, BigDecimal bd) {
		this.name = name;
		this.type = "line".equalsIgnoreCase(type) ? TYPE_LINE : "bar".equalsIgnoreCase(type) ? TYPE_BAR : TYPE_LINE;
		this.color = color;
		this.start = start;
		this.end = end;
		this.data[itemIndex] = bd;
	}

	public BigDecimal[] getData() {
		return data;
	}

	public void setData(BigDecimal[] data) {
		this.data = data;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 获取y轴数据
	 */
	public double[] getY() {
		return getY(1);
	}
	
	/**
	 * 获取相应倍数的y轴坐标
	 */
	public double[] getY(int dw){
		List list = new ArrayList();
		for (int i = 0; i < data.length; i++) {
			BigDecimal bd = data[i];
			if (bd != null) {
				list.add(bd);
			}
		}
		double[] arr = new double[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((BigDecimal) list.get(i)).divide(new BigDecimal(dw), BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return arr;
	}

	/**
	 * 获取x轴坐标，默认偏移1
	 */
	public int[] getX() {
		return getX(1);
	}

	/**
	 * 获取偏移后的X轴坐标
	 */
	public int[] getX(int skewing) {
		List list = new ArrayList();
		for (int i = start; i <= end; i++) {
			if (data[i] != null) {
				list.add(new Integer(i + skewing));
			}
		}
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((Integer) list.get(i)).intValue();
		}
		return arr;
	}
}
