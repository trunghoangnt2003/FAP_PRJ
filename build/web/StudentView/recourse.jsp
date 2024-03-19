<%-- 
    Document   : recourse
    Created on : Mar 20, 2024, 2:01:03 AM
    Author     : trung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FAP</title>
        <script>
            function goBack() {
                window.history.back();
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
                <br>
                <button onclick="goBack()" class="btn btn-light" data-mdb-ripple-init data-mdb-ripple-color="dark">Back</button>
                <br>
                <br>
                <h1>LIST COURSE</h1>
                <br>
                <br>
                <br>
                <div class="container">
                    <span><b> Số dư: ${requestScope.sodu}</b>  <i style="color: red">${requestScope.error}</i></span>
                <table class="table table-hover">

                    <thead>

                        <tr>
                            <td class="table-dark col-md-6">Course</td>
                            <td class="table-dark col-md-3">Semester</td>
                            <td class="table-dark col-md-2" >Price</td>
                            <td class="table-dark col-md-1">Select</td>
                        </tr>


                    </thead>
                    <c:forEach items="${requestScope.list}" var="l">
                        <tr>
                        <td class="table-secondary col-md-6">${l.course.nameCourse} ( ${l.course.codeCourse} )</td>
                        <td class="table-secondary col-md-3">${l.semester.codeSemester}</td>
                        <td class="table-secondary col-md-2" >${requestScope.price}</td>
                        <td class="table-secondary col-md-1">
                            <form action="recourse" method="post">
                                <input type="hidden" name="course" value="${l.course.idCourse}">
                                <input type="hidden" name="semester" value="${l.semester.idSemester}">
                                
                                <input type="submit" value="Đăng kí">
                            </form>
                            
                        </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
