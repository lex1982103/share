package lerrain.project.insurance.product;

import lerrain.tool.formula.Formula;

import java.util.ArrayList;
import java.util.List;

public class Portfolio extends ArrayList
{
	private static final long serialVersionUID = 1L;

	public void addProduct(Insurance parent, Insurance product, InitValue value, Formula c, String desc)
	{
		this.add(new InsuranceRecom(parent, product, value, c, desc));
	}
}
