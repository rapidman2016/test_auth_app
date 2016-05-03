<%@ page import="com.test.websocket.auth.api.ApiToken" %>
<%@ page import="com.test.websocket.auth.api.MessageUtils" %>
<%@ page import="com.test.websocket.auth.api.dao.IApiTokenDao" %>
<%@ page import="com.test.websocket.auth.api.dao.ICustomerDao" %>
<%@ page import="com.test.websocket.auth.api.model.Customer" %>
<%@ page import="com.test.websocket.auth.core.StatisticsCollector" %>
<%@ page import="com.test.websocket.auth.core.WebSocketClient" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="org.springframework.data.domain.Sort" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="javax.websocket.MessageHandler" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Test websocket authentication application</title>
</head>

<%
    final Logger log = LoggerFactory.getLogger("jsp");
    ICustomerDao customerDao = WebApplicationContextUtils.getRequiredWebApplicationContext(application).getBean(ICustomerDao.class);
    IApiTokenDao apiTokenDao = WebApplicationContextUtils.getRequiredWebApplicationContext(application).getBean(IApiTokenDao.class);

    if ("post".equalsIgnoreCase(request.getMethod())) {
        WebSocketClient client = WebApplicationContextUtils.getRequiredWebApplicationContext(application).getBean(WebSocketClient.class);
        client.setMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String text) {
                StatisticsCollector.getInstance().addServerResponse(text);
            }
        });
        try {
            client.send(MessageUtils.createLoginRequest(request.getParameter("login"), request.getParameter("password")));
        } catch (Exception e) {
            log.error("", e);
            System.out.println(e);
        }
        TimeUnit.SECONDS.sleep(1);
    }
%>
<body>

<hr>
Server response:<%=StatisticsCollector.getInstance().getLastServerResponse()%>
<hr>
<form action="index.jsp" method="post">
    <fieldset>
        <legend>Login form</legend>
        <table>
            <thead>
            <tr>
                <th>Login</th>
                <th>Password</th>
            </tr>
            </thead>
            <tr>
                <td><input type="text" name="login" value="customer@email.com"></td>
                <td><input type="text" name="password" value="customer"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit">
                </td>
            </tr>
        </table>
    </fieldset>
</form>
<hr>
<h3>Exists customers</h3>
<table>
    <tr>
        <th>Customer</th>
    </tr>
    <%
        for (Customer customer : customerDao.findListByProperties(0, 1000, "createDate", Sort.Direction.DESC.name(), null)) {
    %>
    <tr>
        <td><%=customer%>
        </td>
    </tr>
    <%
        }
    %>
</table>
<hr>
<h3>Api token history</h3>
<table>
    <tr>
        <th>Token</th>
    </tr>
    <%
        for (ApiToken token : apiTokenDao.findListByProperties(0, 1000, "createDate", Sort.Direction.DESC.name(), null)) {
    %>
    <tr>
        <td><%=token%>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>
