<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library - Edit Book</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Edit Book</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <form action="/books/edit/${book.id}" method="post">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" class="form-control" required value="${book.title}">
            </div>
            <div class="form-group">
                <label for="isbn">ISBN:</label>
                <input type="text" id="isbn" name="isbn" class="form-control" required value="${book.isbn}">
            </div>
            <div class="form-group">
                <label for="authorId">Author:</label>
                <select id="authorId" name="authorId" class="form-control" required>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}" ${author.id == book.author.id ? 'selected' : ''}>${author.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn">Update Book</button>
            <a href="/books" class="btn" style="background-color: #6c757d;">Cancel</a>
        </form>
    </div>
</body>
</html>
