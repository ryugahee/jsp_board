<%--UserDAO 클래스를 이용해서 실질적인 사용자의 로그인 시도를 처리--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="user.UserDAO"%>
<%--자바스크립트 문장을 작성하기 위한 것--%>
<%@ page import="java.io.PrintWriter"%>
<%--<%@ page import="com.sun.jna.platform.win32.Netapi32Util" %>--%>
<%--건너오는 모든 데이터를 UTF-8로 받음--%>
<%request.setCharacterEncoding("UTF-8"); %>
<%--현재 페이지 안에서만 beans가 사용되도록 함--%>
<jsp:useBean id="user" class="user.User" scope="page"/>
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>
<jsp:setProperty name="user" property="userName"/>
<jsp:setProperty name="user" property="userGender"/>
<jsp:setProperty name="user" property="userEmail"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP 게시판 웹 사이트</title>
</head>
<body>
    <%
        if(user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null || user.getUserGender() == null || user.getUserEmail() == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('입력이 안 된 사항이 있습니다.')");
            script.println("</script>");
        } else {
            UserDAO userDAO = new UserDAO();
            // loginActoion과 매개변수 비교하기
            int result = userDAO.join(user);
            if(result == -1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('이미  존재하는 아이디입니다.');");
                script.println("</script>");
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("location.href = 'main.jsp'");
                script.println("history.back()");
                script.println("</script>");
            }
        }

    %>

</body>
</html>

