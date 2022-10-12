var data = '';

window.onload = checkAuth();

async function getAll() {
    return $.ajax({
        url: "http://localhost:8082/api/order/",
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
        url: "http://localhost:8082/api/order/new",
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
        url: "http://localhost:8082/api/user/",
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
        url: "http://localhost:8082/api/order/mine",
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
        url: "http://localhost:8082/api/user/current",
        type: "GET"
    });
}

function getUsername() {
    return $.ajax({
        url: "http://localhost:8082/api/user/current/name",
        type: "GET"
    });
}

function getUserEmail() {
    return $.ajax({
        url: "http://localhost:8082/api/user/current/email",
        type: "GET"
    });
}

function createUser(username, email) {
    return $.ajax({
       url: "http://localhost:8082/user/register/"+username+"/"+email,
       type: "GET",
       success: function (result) {
           user = result;
           console.log('created user with id: '+result);
       },
       error: function (error) {
           user = error;
           console.log(error);
       }
   });
}

function populate(data) {
    formatTableOrder();
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
    formatTableUser();
    var table = "" ;
    for(var i in data){
        table += "<tr>";
        table += "<td>" + data[i].id+"</td>"
                + "<td>" + data[i].name +"</td>"
                + "<td>" + data[i].email +"</td>"
                + "<td>" + data[i].role +"</td>";
        table += "</tr>";
    }
    document.getElementById("result").innerHTML = table;
    return table;
}

function formatTableUser() {
    var headers = "<tr><th>Id</th><th>Name</th><th>Email</th><th>Role</th></tr>";
    document.getElementById("thead").innerHTML = headers;
}

function formatTableOrder() {
    var headers = "<tr><th>GoodsId</th><th>Quantity</th><th>Status</th><th>AssignedTo</th></tr>";
    document.getElementById("thead").innerHTML = headers;
}

function checkAuth() {
    var user = getUser().then((user) => {
        console.log('found user in DB: '+user.name)
    }).catch((err) => {
        console.log('user not registered in the system. Registering...')
        getUserEmail().then(userEmail => {
            console.log('username from social media: '+userEmail);
            getUsername().then(username => {
                createUser(username, userEmail);
            })

        });
    })
}