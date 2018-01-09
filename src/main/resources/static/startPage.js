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
                var path = result.path;
                if(path!= null){
                    path = path.toString();
                    if(path.indexOf("iron")!=-1|| path.indexOf("Machine")!=-1||path.indexOf("furniture")!= -1) {
                        console.log("path type")
                        window.location = "main.html"
                    }
                    else if (path.indexOf("bucket")!=-1) {
                        console.log("path buy")

                        window.location = "bucket.html"
                    }
                    else if (path.indexOf("info")!=-1){
                        console.log("path info")
                        window.location = "info.html"
                    }
                    localStorage.setItem("config", result.configuration);
                }
                else
                window.location = "previewPage.html";
            }
        },
        error: function (result) {
        }
    });

});
function logout() {
    $.ajax({
        type: "POST",
        url: "/logout",success: function (b) {
            localStorage.clear()
            window.location = "index.html"

        }});

}
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
                console.log(result)
                localStorage.setItem("userID", result);
                window.location = "main.html"
            }

        },
        error: function (result) {

        }
    });

});