<%-- 
    Document   : tester
    Created on : 07-Nov-2015, 16:31:42
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
        <form method="post" action="PostVerifiedContent">
            <input type="text" name="data" id="data" placeholder="data"/><br/>
            <input type="text" name="dataType" id="dataType" placeholder="dataType"/><br/>
            <textarea style="width: 400px; height: 80px;" name="subjectPublicKey" id="subjectPublicKey" placeholder="Subject Public Key"></textarea>
            <br/>
            <input type="submit" value="Go"/>
        </form>
    </body>
</html>
