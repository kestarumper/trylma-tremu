$(document).ready(() => {
    console.log("wsconn.js init");

    let WS = window['MozWebSocket'] ? MozWebSocket : WebSocket

    let data = {
        type: "greet",
        value: "dupnij se lolka czlowieniu"
    };

    let connection = new WS($("#roomlist").data("ws-url"));

    console.log(connection);

    connection.onmessage = function (event) {
        let data = JSON.parse(event.data);
        console.log(data);

        $("#room_list").empty();
        for(let user in data) {
            $("#room_list").append(`
                <a href="/room/${data[user].room.owner}" class="list-group-item">
                    <h4 class="list-group-item-heading">${data[user].room.name}</h4>
                    <p class="list-group-item-text">${data[user].room.mode}</p>
                </a>
            `);
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
