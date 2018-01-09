$.ajax({
    url: "/bucket/" + (localStorage.getItem("userID")),
    type: "GET",
    success: function (result) {

    }
});

function buy() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/buy/" + parseInt(localStorage.getItem("userID")),
        success:function (result) {
            window.location = "main.html"

        }
    });

}
function logout() {
    $.ajax({
        type: "POST",
        url: "/logout",success: function (b) {
            localStorage.clear()
            window.location = "index.html"

        }});

}