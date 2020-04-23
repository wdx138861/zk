<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
    <form action="jsp/welcome" method="post">
        姓名：<input type="text" name="name"> <br>
        年龄：<input type="text" name="age"> <br>
        <input type="submit" value="注册">
    </form>
    <hr>
    <form action="jsp/find" method="post">
        用户id：<input type="text" name="id"> <br>
        <input type="submit" value="查询">
    </form>
    <hr>
    <a href="jsp/userCount">查询用户总人数</a>
</body>
</html>
