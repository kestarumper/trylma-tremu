$(document).ready(() => {
    console.log("wsconn.js init");

    let WS = window['MozWebSocket'] ? MozWebSocket : WebSocket

    let data = {
        type: "greet",
        value: "dupnij se lolka czlowieniu"
    };

    let connection = new WS($("#roomdetails").data("ws-url"));

    console.log(connection);

    connection.onmessage = function (event) {
        let data = JSON.parse(event.data);
        console.log(data);

        $("#player_list").empty();
        for(let i = 0; i < data.users.length; i++) {
            $("#player_list").append(`<li class="list-group-item">${data.users[i]}</li>`);
        }

    };
    connection.onopen = function (event) {
        connection.send(JSON.stringify(data));

        setInterval(function () {
            // data.value = $("#messagebox").val();
            connection.send(JSON.stringify(data));
        }, 5000);
        // connection.send(data);
        console.log("Sending " + JSON.stringify(data));
    };
});
