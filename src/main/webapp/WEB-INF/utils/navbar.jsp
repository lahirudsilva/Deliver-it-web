<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand mb-0 h1" href="#">Deliver<span style="color: #f76e40;">it.</span></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <sec:authorize access="hasRole('ADMIN')">
        <div class="collapse navbar-collapse" id="navbarNav1">

            <div class="navbar-nav">
                <a class="nav-item nav-link ${param.page == "home" ? "active" : null}" href="/">Home</a>
                <a class="nav-item nav-link ${param.page == "shipments" ? "active" : null}"
                   href="/students">Shipments</a>
                <a class="nav-item nav-link ${param.page == "drivers" ? "active" : null}" href="/drivers">Drivers</a>
                <a class="nav-item nav-link ${param.page == "customers" ? "active" : null}" href="/rooms">Customers</a>
            </div>
        </div>
        </sec:authorize>
        <sec:authorize access="hasRole('CUSTOMER')">
        <div class="collapse navbar-collapse" id="navbarNav2">
            <div class="navbar-nav">
                <a class="nav-item nav-link ${param.page == "home" ? "active" : null}" href="/">Home</a>
                <a class="nav-item nav-link ${param.page == "sendPackage" ? "active" : null}" href="/createPackage">Send Package</a>
                <a class="nav-item nav-link ${param.page == "tracking" ? "active" : null}" href="/classes">Tracking</a>
                <a class="nav-item nav-link ${param.page == "history" ? "active" : null}"
                   href="/myPackages">History</a>
            </div>

        </div>
        </sec:authorize>
        <div>
            <a class="btn btn-outline-secondary my-2 my-sm-0 logout-btn settings-btn" data-toggle="modal"
               data-target="#settingsModal">Settings</a>
            <a class="btn btn-outline-danger my-2 my-sm-0 logout-btn" href="/logout">
                Logout
            </a>
        </div>

    </div>

</nav>