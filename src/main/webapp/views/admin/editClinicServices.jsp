<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cchc.bean.ClinicBean" %>
<%@ page import="com.cchc.bean.ServiceBean" %>
<%@ page import="com.cchc.bean.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserBean currentUser = (UserBean) session.getAttribute("currentUser");
    ClinicBean clinic = (ClinicBean) request.getAttribute("clinic");
    ArrayList<ServiceBean> allServices = (ArrayList<ServiceBean>) request.getAttribute("allServices");
    ArrayList<ServiceBean> currentServices = (ArrayList<ServiceBean>) request.getAttribute("currentServices");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>設定 <%= clinic.getName() %> 服務 / Edit Services - CCHC</title>
    <style>
        body { font-family: Arial; background:#f4f7f6; margin:0; padding:20px; }
        .container { max-width:1100px; margin:auto; background:white; padding:30px; border-radius:12px; box-shadow:0 0 15px rgba(0,0,0,0.1); }
        table { width:100%; border-collapse:collapse; margin:20px 0; }
        th, td { padding:14px; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#343a40; color:white; }
        .checkbox { margin-right:10px; }
        input[type="number"] { width:80px; padding:8px; }
        button { padding:12px 25px; font-size:16px; border:none; border-radius:8px; cursor:pointer; }
        .save-btn { background:#28a745; color:white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🔗 設定診所服務與名額</h2>
        <h3>診所：<%= clinic.getName() %></h3>
        <p>管理員：<%= currentUser.getFullName() %></p>

        <form method="post" action="${pageContext.request.contextPath}/admin/saveClinicServices.do">
            <input type="hidden" name="clinicId" value="<%= clinic.getClinicId() %>">

            <table>
                <tr>
                    <th width="40px"></th>
                    <th>服務名稱 / Service Name</th>
                    <th>描述 / Description</th>
                    <th>配額 (Quota)</th>
                </tr>
                <% for (ServiceBean s : allServices) { 
                    boolean isChecked = false;
                    int quota = 10;
                    for (ServiceBean cs : currentServices) {
                        if (cs.getServiceId() == s.getServiceId()) {
                            isChecked = true;
                            quota = cs.getQuota() > 0 ? cs.getQuota() : 10;
                            break;
                        }
                    }
                %>
                <tr>
                    <td>
                        <input type="checkbox" name="serviceId" value="<%= s.getServiceId() %>" 
                               <%= isChecked ? "checked" : "" %> class="checkbox">
                    </td>
                    <td><strong><%= s.getServiceName() %></strong></td>
                    <td><%= s.getDescription() != null ? s.getDescription() : "-" %></td>
                    <td>
                        <input type="number" name="quota_<%= s.getServiceId() %>" 
                               value="<%= quota %>" min="1" max="100">
                    </td>
                </tr>
                <% } %>
            </table>

            <br>
            <button type="submit" class="save-btn">💾 儲存設定 / Save Services & Quota</button>
        </form>

        <br><br>
        <a href="${pageContext.request.contextPath}/admin/manageClinicServices.do">
            <button>← 返回診所列表 / Back to Clinic List</button>
        </a>
    </div>
</body>
</html>