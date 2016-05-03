<%@ page import="com.test.websocket.auth.client.WebSocketClient" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="javax.websocket.MessageHandler" %>
<%@ page import="com.test.websocket.auth.api.MessageUtils" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Test websocket authentication application</title>
</head>

<%
    final StringBuilder serverResponse = new StringBuilder();
    if ("post".equalsIgnoreCase(request.getMethod())) {
        WebSocketClient client = WebApplicationContextUtils.getRequiredWebApplicationContext(application).getBean(WebSocketClient.class);
        client.setMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String text) {
               serverResponse.append(text);
            }
        });
        client.send(MessageUtils.createLoginRequest(request.getParameter("login"), request.getParameter("password")));
        TimeUnit.SECONDS.sleep(3);
    }
%>
<body>

Server response:<%=serverResponse.toString()%>

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
                <td><input type="text" name="login"></td>
                <td><input type="text" name="password"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit">
                </td>
            </tr>
        </table>
    </fieldset>
</form>



</body>
</html>
