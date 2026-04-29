/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.tag;

/**
 *
 * @author firetruck
 */
import com.cchc.bean.ServiceBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ServiceSelectTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        Object attr = getJspContext().findAttribute("serviceList");

        out.println("<select name=\"serviceId\">");
        out.println("<option value=\"0\">All Service</option>");

        if (attr instanceof ArrayList) {
            ArrayList<ServiceBean> services = (ArrayList<ServiceBean>) attr;
            for (ServiceBean s : services) {
                out.println(String.format("<option value=\"%d\">%s</option>",
                        s.getServiceId(), s.getServiceName()));
            }
        }

        out.println("</select>");
    }
}
