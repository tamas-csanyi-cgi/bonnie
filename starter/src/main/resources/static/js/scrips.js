var data = '';

window.onload = checkAuth();

async function getAll() {
    return $.ajax({
        url: "http://localhost:8082/api/order/getAll",
        type: "GET",
        success: function (result) {
            populate(result);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

async function getUnassigned() {
    return $.ajax({
        url: "http://localhost:8082/api/order/getUnassigned",
        type: "GET",
        success: function (result) {
            populate(result);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

async function getUsers() {
    return $.ajax({
        url: "http://localhost:8082/api/user/getAll",
        type: "GET",
        success: function (result) {
            populateUser(result);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

async function getMine() {
    return $.ajax({
        url: "http://localhost:8082/api/order/getMine",
        type: "GET",
        success: function (result) {
            populate(result);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

async function getUser() {
    return $.ajax({
        url: "http://localhost:8082/api/user/get/current",
        type: "GET"
    });
}

function getUsername() {
    return $.ajax({
        url: "http://localhost:8082/api/user/get/current/name",
        type: "GET"
    });
}

function createUser(username) {
    return $.ajax({
       url: "http://localhost:8082/api/user/add",
       type: "POST",
       contentType: 'application/json',
       data: JSON.stringify({
           "name": username,
           "password": "secret",
           "role": "ASSEMBLER"
       }),
       dataType: 'json',
       processData: false,
       success: function (result) {
           user = result;
           console.log('3'+result);
       },
       error: function (error) {
           user = error;
           console.log(error);
       }
   });
}

function populate(data) {
    var table = "" ;
    for(var i in data){
        var assignedTo = '-';
        if (data[i].assignedTo) {
            assignedTo = data[i].assignedTo.name;
        }
        table += "<tr>";
        table += "<td>"
                + data[i].goodsId +"</td>"
                + "<td>" + data[i].quantity +"</td>"
                + "<td>" + data[i].status +"</td>"
                + "<td>" + assignedTo +"</td>";
        table += "</tr>";
    }
    document.getElementById("result").innerHTML = table;
    return table;
}

function populateUser(data) {
    var table = "" ;
    for(var i in data){
        table += "<tr>";
        table += "<td>"
                + data[i].name +"</td>"
                + "<td>" + data[i].role +"</td>"
                + "<td></td>"
                + "<td></td>";
        table += "</tr>";
    }
    document.getElementById("result").innerHTML = table;
    return table;
}

function checkAuth() {
    var user = getUser().then((user) => {
        console.log('found user in DB: '+user.name)
    }).catch((err) => {
        console.log('couldnt find user')
        getUsername().then(username => {
            console.log('username: '+username);
            createUser(username);
        });
    })
    /*if (user.name == '') {
        var username = getUsername();
        $.ajax({
            url: "http://localhost:8082/api/user/add",
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify({
                "name": "username",
                "password": "secret",
                "role": "ASSEMBLER"
            }),
            dataType: 'json',
            processData: false,
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
    }*/
}