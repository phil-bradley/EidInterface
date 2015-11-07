<%-- 
    Document   : testverify
    Created on : 07-Nov-2015, 17:46:31
    Author     : philb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body
        Test sending verified content<br/>
        <br/>
        <form method="post" action="QueryContent">
            <input type="text" name="data" id="data" placeholder="dataToVerify"/><br/>
            <input type="text" name="eid" id="eid" placeholder="EID"/><br/>
            <br/>
            <input type="submit" value="Go"/>
        </form>
    </body>
</html>