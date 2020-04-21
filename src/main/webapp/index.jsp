<%@page import="java.util.*" pageEncoding="utf-8" %>
<html>

<head>
    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#button").click(function(){
            var file=new FormData();
            file.append("file",$("#file")[0].files[0]);
            $.ajax({
                url:"${pageContext.request.contextPath}/file/upload",
                type:"post",
                async:false,
                processData:false,
                contentType:false,
                data:file,
                success:function (success) {
                    alert(success);
                },
                error:function(){
                    alert("出错了");
                }
            })
        })

    })
</script>
</head>
<body>
<form method="post">
<input type="file" id="file" /><br/>
    <input type="button" value="提交" id="button"/>
    <input type="button" value="下载" onclick="location.href='${pageContext.request.contextPath}/file/download?fileName=day1 第二节课.wmv'"/>
</form>
</body>
</html>
