package lerrain.project.insurance.plan.filter.table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Table implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String name;
	
	Blank[][] title = new Blank[10][80];
	Blank[][] grid = new Blank[200][80];
	
	int maxRow;
	int maxCol;
	int titleHeight;
	
	Map additional;
	
	public int getMaxRow()
	{
		return maxRow;
	}

	public int getMaxCol()
	{
		return maxCol;
	}
	
	public int getTitleHeight()
	{
		return titleHeight;
	}

	public void setBlank(int row, int col, int rowspan, int colspan, String text)
	{
		setBlank(row, col, 1, 1, text, null);
	}
	
	public void setBlank(int row, int col, int rowspan, int colspan, String text, String align)
	{
		grid[row][col] = new Blank(rowspan, colspan, text, align);
		
		for (int i=0;i<rowspan;i++)
			for (int j=0;j<colspan;j++)
				if (grid[row+i][col+j] == null)
					grid[row+i][col+j] = new Blank();
		
		if (row + rowspan> maxRow)
			maxRow = row + rowspan;

		if (col + colspan> maxCol)
			maxCol = col + colspan;
	}
	
	public void setBlank(int row, int col, String text)
	{
		setBlank(row, col, 1, 1, text);
	}
	
	public void setTitleBlank(int row, int col, int rowspan, int colspan, String text)
	{
		title[row][col] = new Blank(rowspan, colspan, text);
		
		for (int i=0;i<rowspan;i++)
			for (int j=0;j<colspan;j++)
				if (title[row+i][col+j] == null)
					title[row+i][col+j] = new Blank();
		
		if (row + rowspan> titleHeight)
			titleHeight = row + rowspan;

		if (col + colspan> maxCol)
			maxCol = col + colspan;
	}
	
	public void setTitleBlank(int row, int col, String text)
	{
		setTitleBlank(row, col, 1, 1, text);
	}

	public Blank getBlank(int row, int col)
	{
		return grid[row][col];
	}
	
	public Blank getTitleBlank(int row, int col)
	{
		return title[row][col];
	}
	
	public void setAdditional(String name, Object value)
	{
		if (additional == null)
			additional = new HashMap();
		
		additional.put(name, value);
	}
	
	public Object getAdditional(String name)
	{
		if (additional == null)
			return null;
		
		return additional.get(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
