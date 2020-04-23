<%@page import="java.util.*" pageEncoding="utf-8" %>
<html>

<head>
<%--    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
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
</script>--%>
</head>
<body>
<form method="post" action="/minio/minioOperation/uploadFile" enctype="multipart/form-data">
<input type="file" id="file" name="file" />
    <input type="hidden" id="bucketName" name="bucketName" value="kkx"/>
    <input type="submit" value="提交"/>

</form>
<form method="post" action="/minio/minioOperation/downloadFile" enctype="multipart/form-data">
   文件名称: <input type="text" name="file" value=""/>
   桶名称: <input type="text" name="bucketName" value=""/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
