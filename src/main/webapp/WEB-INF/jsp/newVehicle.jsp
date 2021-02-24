<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
    <h1>New Vehicle Registration</h1>
        
    <form:form modelAttribute="form">
        <form:errors path="" element="div" />
        <div>
            <form:label path="vehicleName">Name</form:label>
            <form:input path="vehicleName" />
            <form:errors path="vehicleName" />
        </div>
        <div>
            <input type="submit" />
        </div>
    </form:form>
</body>
</html>
