package lerrain.tool.data;

import java.io.Serializable;

public class DataRecord implements Serializable
{
	private static final long serialVersionUID = 1L;

	double[][] v;
	
	public DataRecord(double[][] v)
	{
		this.v = v;
	}
	
	public DataRecord(int h, int w)
	{
		v = new double[h][w];
	}
	
	public void set(int y, int x, int value)
	{
		v[y][x] = value;
	}
	
	public void set(int y, int x, String value)
	{
		v[y][x] = Double.parseDouble(value);
	}

	public void set(int y, int x, double value)
	{
		v[y][x] = value;
	}
	
	public double[][] value()
	{
		return v;
	}

//	public Object run(Factors p)
//	{
//		return v[Value.valueOf(p.get("#1")).intValue()][Value.valueOf(p.get("#2")).intValue()];
//	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<v.length;i++)
		{
			if (i!=0)
				sb.append("\n");
			for (int j=0;j<v[0].length;j++)
			{
				if (j!=0)
					sb.append(",");
				sb.append(v[i][j]);
			}
		}
		return sb.toString();
	}
}
