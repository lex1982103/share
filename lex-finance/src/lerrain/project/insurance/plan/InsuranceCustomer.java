package lerrain.project.insurance.plan;

import java.util.Date;

import lerrain.tool.formula.Factors;

public interface InsuranceCustomer extends Factors
{
	public static final int GENDER_MALE			= 1;
	public static final int GENDER_FEMALE		= 2;
	
	public String getId();

	public Date getBirthday();
	
	public int getGenderCode();
	
	public int getOccupationCategory(String typeCode);
}
