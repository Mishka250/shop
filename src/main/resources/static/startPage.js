$("#sendLogin").click(function (e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/login",
        data: {
            // id: $(this).val(), // < note use of 'this' here
            // access_token: $("#access_token").val()
            email: $('#loginEmail').val(),
            password: $('#loginPassword').val()
        },
        success: function (result) {

            if (result == null) {
                alert("Please, check email and/or password");
            }
            else {
                console.log(result)
                localStorage.setItem("userID", result.userID);
                if(result.configuration!=null) {
                    localStorage.setItem("typeRequest", result.configuration.type);
                }
                if(result.path!= null){
                    localStorage.setItem("path", result.configuration.type);
                    localStorage.setItem("config", result.configuration);
                }
                alert(result);
                window.location = "previewPage.html";
            }
        },
        error: function (result) {
        }
    });

});
$("#registerButton").click(function (e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/register",
        data: {
            username: $('#usernameId').val(),
            email: $('#emailId').val(),
            password: $('#passwordId').val(),
            phone: $('#phoneId').val()
        },
        success: function (result) {
            if (result == -1) {
                alert("This email is already using, try different");
            }
            else {
                window.location = "main.html"
            }

        },
        error: function (result) {

        }
    });

});