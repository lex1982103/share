package lerrain.tool.data;

import java.io.Serializable;
import java.util.Map;


public interface DataParser extends Serializable
{
	public DataSource newSource(Map param);
}
