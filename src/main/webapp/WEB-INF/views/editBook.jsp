<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の編集｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/lightbox.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/lightbox.js" /></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />       
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <div>
        <form action="<%=request.getContextPath()%>/updateBook" method="post" enctype="multipart/form-data" id="data_upload_form">
            <h1>書籍の編集</h1>
            <div class="content_body add_book_content">
                
                <div>
                    <span>書籍の画像</span> <span class="care care1">任意</span>
                    <div class="book_thumnail">
                        <img class="book_noimg" src="resources/img/noImg.png">
                    </div>
                    <input type="file" accept="image/*" name="thumbnail" id="thumbnail">
                </div>
                <div class="content_right">
                
                    <c:if test="${!empty errorMessages}">
                        <div class="error"> 
                            <c:forEach var="errorMessage" items="${errorMessages}">
                                <p> ${errorMessage}</p>
                            </c:forEach>   
                        </div>
                    </c:if>
                    
                    <div>
                        <span>書籍名</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookeditInfo}">
                            <input type="text" name="title" value="${bookeditInfo.title}">
                        </c:if>
                        <c:if test="${empty bookeditInfo}">
                            <input type="text" name="title" autocomplete="off">
                        </c:if>
                    </div>
                    <div>
                        <span>著者名</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookeditInfo}">
                            <input type="text" name="author" value="${bookeditInfo.author}">
                        </c:if>
                        <c:if test="${empty bookeditInfo}">
                            <input type="text" name="author" autocomplete="off">
                        </c:if>
                    </div>
                    <div>
                        <span>出版社</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookeditInfo}">
                            <input type="text" name="publisher" value="${bookeditInfo.publisher}">
                        </c:if>
                        <c:if test="${empty bookeditInfo}">
                            <input type="text" name="publisher">
                        </c:if>
                    </div>
                     <div>
                        <span>出版日</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookeditInfo}">
                            <input type="text" name="publish_date" value="${bookeditInfo.publish_date}">
                        </c:if>
                        <c:if test="${empty bookeditInfo}">
                            <input type="text" name="publish_date">
                        </c:if>
                    </div>
                     <div>
                        <span>ISBN</span><span class="care care1">任意</span>
                        <c:if test="${!empty bookeditInfo}">
                            <input type="text" name="isbn" value="${bookeditInfo.isbn}">
                        </c:if>
                        <c:if test="${empty bookeditInfo}">
                            <input type="text" name="isbn">
                        </c:if>
                    </div>
                     <div>
                        <span>説明</span><span class="care care1">任意</span>
                        <c:if test="${!empty bookeditInfo}">
                            <input type="text" name="explanation" value="${bookeditInfo.explanation}">
                        </c:if>
                        <c:if test="${empty bookeditInfo}">
                            <input type="text" name="explanation">
                        </c:if>
                    </div>
                    <input type="hidden" id="bookId" name="bookId" value="${bookeditInfo.bookId}">
                </div>
            </div>
            <div class="addBookBtn_box">
                <button type="submit" class="btn_addBook">更新</button>
            </div>
        </form>
        </div>
    </main>
</body>
</html>