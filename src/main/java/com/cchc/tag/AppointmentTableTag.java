/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cchc.tag;

/**
 *
 * @author firetruck
 */
import com.cchc.bean.AppointmentBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AppointmentTableTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        Object attr = getJspContext().findAttribute("appointments");

        if (attr instanceof ArrayList) {
            ArrayList<AppointmentBean> abs = (ArrayList<AppointmentBean>) attr;
            if (abs.isEmpty()) {
                out.println("<tr><td colspan='8' style='padding: 20px; text-align: center; color: gray;'>No relevant appointment information found.</td></tr>");
                return;
            }

            for (AppointmentBean ab : abs) {
                out.println("<tr>");
                out.println("<td style='padding: 10px; text-align: center;'>" + ab.getAppointmentId() + "</td>");

                out.println("<td>" + (ab.getFullName() != null ? ab.getFullName() : "User " + ab.getUserId()) + "</td>");

                out.println("<td>" + (ab.getServiceName() != null ? ab.getServiceName() : "N/A") + "</td>");

                out.println("<td>" + ab.getAppointmentDate() + "</td>");

                out.println("<td>" + ab.getAppointmentTime() + "</td>");

                out.println("<td style='text-align: center;'>");
                out.println("  <span class='status-badge status-" + ab.getStatus().toLowerCase() + "'>");
                out.println("    " + ab.getStatus());
                out.println("  </span>");
                out.println("</td>");

                String reason = (ab.getCancelReason() != null) ? ab.getCancelReason() : "";
                out.println("<td style='color: #666; font-size: 0.9em;'>" + reason + "</td>");

                out.println("<td style='text-align: center;'>");
                out.println("  <a href='EditAppServlet?id=" + ab.getAppointmentId() + "' style='color: #007bff; text-decoration: none;'>Edit</a> | ");
                out.println("  <a href='DeleteAppointmentServlet?id=" + ab.getAppointmentId() + "' ");
                out.println("     style='color: red; text-decoration: none;' ");
                out.println("     onclick=\"return confirm('Are you sure you want to delete this appointment? This cannot be undone.')\">Delete</a>");

                out.println("</td>");

                out.println("</tr>");
            }
        } else {
            out.println("<tr><td colspan='8' style='padding: 20px; text-align: center; color: gray;'>Please enter your criteria and then click search.</td></tr>");
        }

    }
}
