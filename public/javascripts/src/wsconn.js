$(document).ready(() => {
    console.log("wsconn.js init");
    var connection = new WebSocket("ws://localhost:9000/ws");
    console.log(connection);
});
