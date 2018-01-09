var info = $.get("review/" + localStorage.getItem("type") + "/" + localStorage.getItem("productId"));

info = JSON.parse(info)
console.error(info)
var reviewsFromInfo = info.reviews;
var iron = info.iron;
$("#reviewId").empty();
$("#mark").empty();
getAverageMark();
checkMark();
for (var i = 0; i < reviewsFromInfo.length; i++) {
alert("inside loop")
    console.error(reviewsFromInfo);

    $("#reviewId").append(
        "<div><h4>" + reviewsFromInfo[i].user + " " + reviewsFromInfo[i].time.dayOfMonth + ":" + reviewsFromInfo[i].time.monthOfYear +
        ":" + reviewsFromInfo[i].time.year + " at " + reviewsFromInfo[i].time.hourOfDay + ":" + reviewsFromInfo[i].time.minuteOfHour +
        "  wrote: <h5>" + reviewsFromInfo[i].body +
        "</h5></h4></div>"
    )
}

$("#infoId").append(
    "<div><h4>Name: " + iron.product.name +
    "<br>Type: " + iron.ironType.type +
    "<br>Power: " + iron.power + "w" +
    "<br>Has vapour: " + iron.isVapor +
    "<br>Price: " + iron.product.price +
    "<br>Creator: " + iron.product.creator +
    "</h4></div>"
);
function sendMark(mark) {
    $.ajax({
        type: "POST",
        url: "/mark/add",
        data:{
            idUser: localStorage.getItem("userID") ,
            mark:mark,
            type:localStorage.getItem("type"),
            productId:localStorage.getItem("productId")
        },
        success: function (result) {
           getAverageMark();
        },
        error: function (result) {
        }
    });
}
function hasMark() {
    return $.ajax({
        type: "GET",
        url: "/hasMark/" + localStorage.getItem("userID") + "/" + localStorage.getItem("productId")+"/"+localStorage.getItem("type")
    });
}
function hasEntry() {
    return $.ajax({
    type: "GET",
        url: "/bucket/hasEntry/"+localStorage.getItem("userID")+ "/" +
        localStorage.getItem("type")+"/" + localStorage.getItem("productId")
});

}
function checkMark() {

    $.when(hasMark(),hasEntry()).done(function (r1, r2) {
        if(!r1[0] && r2[0]) {
            $("#markGeneration").append(
                "<div> " +
                '<button onclick="sendMark(1)">1</button>' +
                '<button onclick="sendMark(2)">2</button>' +
                '<button onclick="sendMark(3)">3</button>' +
                '<button onclick="sendMark(4)">4</button>' +
                '<button onclick="sendMark(5)">5</button>' +
                "</div>"
            )
        }});
}

function getAverageMark() {

    $.ajax({
        type: "GET",
        url: "/mark/" + localStorage.getItem("type") + "/" + localStorage.getItem("productId"),
        success: function (result) {
            $("#mark").empty();
            $("#mark").append(
                '<div>Average mark: ' + result +
                "</div>"
            )
            if($("#markGeneration").size != 0){
                $("#markGeneration").empty();
            }
        },
        error: function (result) {
        }
    });
}

function addReview() {

    var res;
    $.ajax({
        type: "POST",
        url: "/addReview",
        data: {
            userId: parseInt(localStorage.getItem("userID")),
            text: $("#textReview").val(),
            type: localStorage.getItem("type"),
            idProduct: localStorage.getItem("productId")
        },
        success: function (result) {
            res = result
            if (result != null) {
                $("#reviewId").append(
                    "<div><h4>" + result.userName + " " + result.time.dayOfMonth + ":" + result.time.monthOfYear +
                    ":" + result.time.year + " at " + result.time.hourOfDay + ":" + result.time.minuteOfHour +
                    "  wrote: <h5>" + result.body +
                    "</h5></h4></div>"
                )
            }
            else {
            }
        },
        error: function (result) {
        }
    });

}