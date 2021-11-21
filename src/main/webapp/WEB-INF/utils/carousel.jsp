<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="../../images/c-image.jpg" class="d-block w-100 c-image" alt="banner" >
            <div class="carousel-caption d-none d-md-block">
                <h1 class="c-title" >${param.page}</h1>
                <sec:authorize access="hasRole('CUSTOMER')">
                    <a type="button" class="btn btn-outline-dark create-package" href="/createPackage"> Create Package</a>
                </sec:authorize>
            </div>
        </div>
    </div>
</div>

