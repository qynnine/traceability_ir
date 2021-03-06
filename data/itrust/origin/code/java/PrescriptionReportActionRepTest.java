package edu.ncsu.csc.itrust.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PrescriptionReportBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

@SuppressWarnings("unchecked")
public class PrescriptionReportActionRepTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private PrescriptionReportAction action;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetNoPrescriptionReports() throws Exception {
		action = new PrescriptionReportAction(factory, 9000000000L, "2");
		List<OfficeVisitBean> officeVisits = action.getAllOfficeVisits();
		Map params = new HashMap();
		params.put("ov1", new String[]{"1"});
		List<PrescriptionReportBean> prescriptionReports = action.getPrescriptionReports(params, officeVisits);
		assertEquals(0, prescriptionReports.size());
	}
	
	public void testRepresentPatient() throws Exception {
		action = new PrescriptionReportAction(factory, 2L, "2");
		assertEquals(1L, action.representPatient("1"));
	}
	
}
