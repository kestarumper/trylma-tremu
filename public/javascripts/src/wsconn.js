$(document).ready(() => {
    console.log("wsconn.js init");

    let WS = window['MozWebSocket'] ? MozWebSocket : WebSocket

    let data = {
        type: "greet",
        value: "dupnij se lolka czlowieniu"
    };

    let connection = new WS($("body").data("ws-url"));

    console.log(connection);

    connection.onmessage = function (event) {
        console.log(event.data);

    };
    connection.onopen = function (event) {
        setInterval(function () {
            // data.value = $("#messagebox").val();
            connection.send(JSON.stringify(data));
        }, 1000);
        // connection.send(data);
        console.log("Sending " + JSON.stringify(data));
    };
});
