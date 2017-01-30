package com.temperateprogrammer.servlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class CaptureWizardUserServlet
 */
public class VisitorRantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(VisitorRantServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisitorRantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//no implementation
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String recordSetId = request.getParameter("recordSetId");
        String userId = request.getParameter("userId");
        String displayIndex = request.getParameter("displayIndex");
        String email = request.getParameter("email");
        String notes = request.getParameter("notes");
        
		// Call into the Liferay DDLRecord API
		Fields fields = new Fields();
		fields.put(new Field("txtEmail", email));
		fields.put(new Field("htmlNotes", notes));
		
		long ddlRecordPrimaryKey = 0;
		try {
			ServiceContext serviceContext = new ServiceContext();
//			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
//			System.out.println("---groupId=" + themeDisplay.getCompanyGroupId());
			serviceContext.setUserId(new Long(userId).longValue());
			serviceContext.setScopeGroupId(10181);
			DDLRecord ddlRecord = DDLRecordLocalServiceUtil.addRecord(new Long(userId).longValue(), 
								new Long(groupId).longValue(), 
								new Long(recordSetId).longValue(), 
								new Integer(displayIndex).intValue(), 
								fields, 
								serviceContext);
			ddlRecordPrimaryKey = ddlRecord.getPrimaryKey();
		} catch (PortalException pe) {
			pe.printStackTrace();
		} catch (SystemException se) {
			se.printStackTrace();
		}
        
        response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Long(ddlRecordPrimaryKey).toString());
		writer.close();
		//LOG.info("response string=" + ddlRecordPrimaryKey);
		System.out.println("response string=" + ddlRecordPrimaryKey);
	}
}
