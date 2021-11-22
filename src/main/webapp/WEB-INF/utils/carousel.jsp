<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <sec:authorize access="hasRole('CUSTOMER')">
            <img src="../../images/c-image.jpg" class="d-block w-100 c-image" alt="banner" >
            <div class="carousel-caption d-none d-md-block">
                <h1 class="c-title" >${param.page}</h1>

                    <a type="button" class="btn btn-outline-dark create-package" href="/createPackage"> Create Package</a>

            </div>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <img src="../../images/admin-cover.png" class="d-block w-100 c-image" alt="banner" >
                <div class="carousel-caption d-none d-md-block">
                    <h1 class="c-title" >${param.page}</h1>

                    <a type="button" class="btn btn-outline-dark create-package" href="/"> Check Shipments</a>

                </div>
            </sec:authorize>
        </div>
    </div>
</div>

