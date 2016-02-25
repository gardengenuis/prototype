<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>测试UEditor</title>
	<!-- 配置文件 -->  
    <script type="text/javascript" src="<c:url value="/ueditor/ueditor.config.js"/>"></script>  
    <!-- 编辑器源码文件 -->  
    <script type="text/javascript" src="<c:url value="/ueditor/ueditor.all.js"/>"></script>  
    <!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->  
    <script type="text/javascript" src="<c:url value="/ueditor/lang/zh-cn/zh-cn.js"/>"></script> 
</head>
<body>
	<form method="POST" action="<c:url value="/uploadFile.do"/>" enctype="multipart/form-data">
        File1 to upload: <input type="file" name="file"><br /> 
        Name1: <input type="text" name="name"><br /> <br /> 
        <input type="submit" value="Upload"> Press here to upload the file!
    </form>
	<script id="container" name="content" type="text/plain">这里写你的初始化内容</script>  
        <script type="text/javascript">  
            var editor = UE.getEditor('container')  
        </script>  
</body>
</html>