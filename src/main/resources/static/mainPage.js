var maxPrice = -1;
var allProductSize;
var userID;
var price;
var currentPage;
$(document).ready(function () {
    // window.setTimeout(sendUserFilters(),10*1000);
    getUsersFilters();

});

function getUsersFilters() {
    $.ajax({
        url: "/getFilters/" +localStorage.getItem("typeRequest")+"/"+ (localStorage.getItem("userID")),
        type: "GET",
        success: function (result) {
            if (result != null) {

                if (result.configuration != null) {
                    $("#searchByName").val(result.configuration.name)

                    $("#power").val(result.configuration.power);
                    if (result.configuration.vapourSelected) {
                        $("#vapourSelected").prop('checked', true);
                        $('#vapourValue').attr('disabled', false);
                        $("#vapourValue").prop('checked', result.configuration.vapourValue);
                    }
                }
            }
            else {
                $('#vapourValue').attr('disabled', true)
            }
            submitByName()
            $("#custom-handle").val(result.price);
        },
        error: function (result) {
        }
    })
}
function sendUserFilters() {
    var data = /*JSON.stringify*/({
        userId: localStorage.getItem("userID"),
        configuration: {
            name: $("#searchByName").val(),
            price: price,
            furnitureConfig: null,
            sewingMachineConfig: null,
            ironConfig: {
                power: $("#power").val(),
                isVapourSelected: $("#vapourSelected").is(":checked"),
                isVapourValue: $("#vapourSelected").is(":checked") ? $("#vapourValue").is(":checked") : false
            }
        }
    });
    var url = "http://localhost:8080/setFilters/";

    if(localStorage.getItem("typeRequest") === "iron") {
        url += localStorage.getItem("typeRequest") + "/"

    console.warn(url)
    }
    var checked = $("#vapourSelected").is(":checked");

    var data = {userID: localStorage.getItem("userID"),
                name:$("#searchByName").val(),
                price: price,
                power: $("#power").val(),
        isVapourSelected:      checked,
        isVapourValue:(checked ? $("#vapourValue").is(":checked") : false) };
   /* data["userID"] = localStorage.getItem("userID")
    data["name"] =
    data["price"] = price;
    data["power"] =  $("#power").val()
    data["isVapourSelected"] =  checked
    data["isVapourValue"] =  (checked ? $("#vapourValue").is(":checked") : false)*/
    console.warn(data);
    $.ajax({
        url: url,
        contentType: "application/json",
        type: "POST",
        data: JSON.stringify(data),
        success: function (result) {
            console.warn(result)
            submitByFilter()
        },
        error: function (result) {
        }
    });


}

function submitByFilter() {
    $.ajax({
        type: "POST",
        url: "/" + localStorage.getItem("typeRequest"),
        contentType: "application/json",
        data: JSON.stringify({
                name: $("#searchByName").val(),
                price: price,
                power: $("#power").val(),
                isVapourSelected: $("#vapourSelected").is(":checked"),
                isVapourValue: $("#vapourSelected").is(":checked") ? $("#vapourValue").is(":checked") : false
    }),
        success: function (result) {
            $("#tbody").empty();

            for (var i = 0; i < result.length; i++) {
                var message = result[i];
                $("#tbody").append
                ("<tr><th>" + (i + 1) +
                    '</th><th><button onclick= "myClick(\'' + message.type + '\',\'' + message.id + '\')">' + message.name +
                    "</button>" +
                    "</th><th>" + message.price +
                    "</th></tr>"
                );

                if (message.price > maxPrice) {
                    maxPrice = message.price;
                }
                // i++;
            }
            allProductSize = result.length;
            slider();
            // window.location = "info.html"
        }
    });
}

function submitByName() {
    $.ajax({
        type: "GET",
        url: "/getAllByName",
        data: {
            // id: $(this).val(), // < note use of 'this' here
            // access_token: $("#access_token").val()
            name: $("#searchByName").val()
        },
        success: function (result) {
            $("#tbody").empty();

            for (var i = 0; i < result.length; i++) {
                var message = result[i];
                $("#tbody").append
                ("<tr><th>" + (i + 1) +
                    '</th><th><button onclick= "myClick(\'' + message.type + '\',\'' + message.id + '\')">' + message.name +
                    "</button>" +
                    "</th><th>" + message.price +
                    '</th><th><button id="bucket'+ message.id +'" value="add to bucket" onclick= "addToBucket(\'' + message.type + '\',\'' + message.id + '\')">add to bucket</button>' +
                    "</th></tr>"
                );

                if (message.price > maxPrice) {
                    maxPrice = message.price;
                }
                // i++;
            }
            allProductSize = result.length;
            slider();
            // window.location = "info.html"
        }
    });
}

$("#searchByNameSubmit").click(function (e) {
    e.preventDefault();
    submitByName()
});
function addToBucket(type, id) {
    $.ajax({
        url: "/bucket/add",
        dataType: "json",
        type: "POST",
        data: {userID: localStorage.getItem("userID"), type: type, productID: id},
        success: function (result) {
            if(result == true){
                alert("Successfully added to bucket")
                // $("#bucket" +id).attr("onclick", removeFromBucket(type, id));
                document.getElementById("bucket"+id).onclick = function() { removeFromBucket(type,id)};
                document.getElementById("bucket"+id).innerHTML="remove from bucket";
                localStorage.setItem("added", true)
            }
            else {
                alert("something was wrong, didn`t add to bucket")
            }
        },
        error: function (result) {
        }
    });
}
function removeFromBucket(type, id){
    $.ajax({
        url: "/bucket/remove",
        dataType: "json",
        type: "POST",
        data: {userID: localStorage.getItem("userID"), type: type, productID: id},
        success: function (result) {
            if(result == true){
                alert("Successfully removed from bucket")
                document.getElementById("bucket"+id).onclick =function() { addToBucket(type,id)};
                document.getElementById("bucket"+id).innerHTML=("add to bucket")
                localStorage.removeItem("added")
            }
            else {
                alert("something was wrong, didn`t removed from bucket")
            }
        },
        error: function (result) {
        }
    });
}
function myClick(type, id) {
    localStorage.setItem("type", type);
    localStorage.setItem("productId", id);
    $.ajax({
        url:  localStorage.getItem('type') + "/" + localStorage.getItem('productId'),
        dataType: "json",
        type: "GET",
        success: function (result) {

            localStorage.setItem("info", JSON.stringify(result));
            window.location = "info.html"
        },
        error: function (result) {

        }
    });
}
function f(type) {
    $("#filter").empty();

    localStorage.setItem("typeRequest",type);
    if(type ==="iron") {
       addIronFilters()
   }
   else if(type==='machines') {

   }else if(type==='furniture') {

    }
}
function addIronFilters() {
    $("#filter").append("  Power: <input type=\"number\" id=\"power\" value=\"0\" onchange=\"sendUserFilters()\"> <br>\n" +
        "\n" +
        "        <input type=\"checkbox\" id=\"vapourSelected\"  onchange=\"\n" +
        "            if($('#vapourSelected').is(':checked')){\n" +
        "                $('#vapourValue').removeProp('disabled')\n" +
        "            }else {\n" +
        "               $('#vapourValue').attr('disabled',true)\n" +
        "            }\n" +
        "            sendUserFilters();\n" +
        "            \"> If need vapour filter<br>\n" +
        "        <input type=\"checkbox\" id=\"vapourValue\" disabled onchange=\"sendUserFilters()\"> Value of vapour filter<br>\n" +
        "    </div><div><br><br>\n" +
        "      \n" +
        "</div>")

}