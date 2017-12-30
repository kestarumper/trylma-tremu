$(document).ready(() => {
    console.log("wsconn.js init");

    let WS = window['MozWebSocket'] ? MozWebSocket : WebSocket

    let connection = new WS($("#roomdetails").data("ws-url"));

    connection.onmessage = function (event) {
        let data = JSON.parse(event.data);

        console.log(data);

        $("#player_list").empty();
        for(let user in data.users) {
            $("#player_list").append(`<li class="list-group-item ${data.owner.name === data.users[user].name ? "active" : ""}">${data.users[user].name}</li>`);
        }

    };
    connection.onopen = function (event) {
        connection.send(JSON.stringify({
            type: "join",
            value: $("#sessionusername").val()
        }));

        setInterval(function () {
            connection.send(JSON.stringify({
                type: "get/detail",
                value: ""
            }));
        }, 5000);

        $(window).bind('unload', function(){
            connection.send(JSON.stringify({
                type: "leave",
                value: $("sessionusername").val()
            }))
        });
    };
});
