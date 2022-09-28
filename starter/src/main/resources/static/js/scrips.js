var data = '';

async function getAll() {
    var data = '';
    $.ajax({
        url: "http://localhost:8082/api/order/getAll",
        type: "GET",
        success: function (result) {
            data = result;
            console.log('1'+result);
            populate(data);
        },
        error: function (error) {
            data = error;
            console.log(error);
        }
    });
    console.log('2'+data);
    return data;
}

async function getUnassigned() {
    var data = '';
    $.ajax({
        url: "http://localhost:8082/api/order/getUnassigned",
        type: "GET",
        success: function (result) {
            data = result;
            console.log('1'+result);
            populate(data);
        },
        error: function (error) {
            data = error;
            console.log(error);
        }
    });
    console.log('2'+data);
    return data;
}

async function getMine() {
    var data = '';
    $.ajax({
        url: "http://localhost:8082/api/order/getMine",
        type: "GET",
        success: function (result) {
            data = result;
            console.log('1'+result);
            populate(data);
        },
        error: function (error) {
            data = error;
            console.log(error);
        }
    });
    console.log('2'+data);
    return data;
}

async function getUser() {
    var user = '';
    $.ajax({
        url: "http://localhost:8082/api/user/get/current",
        type: "GET",
        success: function (result) {
            user = result;
            console.log('3'+result);
        },
        error: function (error) {
            user = error;
            console.log(error);
        }
    });
    console.log('4'+user);
    return user
}

function populate(data) {
    var table = "" ;

        for(var i in data){
            var assignedTo = '-';
            if (data[i].assignedTo) {
                assignedTo = data[i].assignedTo.name;
            }
            console.log('5'+assignedTo);
            table += "<tr>";
            table += "<td>"
                    + data[i].goodsId +"</td>"
                    + "<td>" + data[i].quantity +"</td>"
                    + "<td>" + data[i].status +"</td>"
                    + "<td>" + assignedTo +"</td>";
            table += "</tr>";
        }
    console.log('6'+data);
    document.getElementById("result").innerHTML = table;
    return table;
}