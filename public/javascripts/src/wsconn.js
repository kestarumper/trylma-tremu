$(document).ready(() => {
    console.log("wsconn.js init");

    let data = {
        type: "greet",
        value: "dupnij se lolka czlowieniu"
    };

    let connection = new WebSocket($("body").data("ws-url"));

    console.log(connection);

    connection.onmessage = function (event) {
        console.log(event.data);

    };
    connection.onopen = function (event) {
        connection.send(JSON.stringify(data));
        console.log("Sending " + JSON.stringify(data));
    };
});
