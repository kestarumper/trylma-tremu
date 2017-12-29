var width = window.innerWidth-200;
var height = window.innerHeight-200;

function addField(layer, stage, xpos, ypos, color) {
    var scale = Math.random();

    var stroke = 'black';
    if(color === 'black'){
        stroke = 'white';
    }

    var hexagon = new Konva.RegularPolygon({
        x: xpos,
        y: ypos,
        sides: 6,
        radius: 24,
        fill: color,
        stroke: stroke,
        strokeWidth: 4,
        draggable: false,
        shadowColor: 'black',
        shadowBlur: 10,
        shadowOffset: {
            x : 5,
            y : 5
        },
        shadowOpacity: 0.6,
        base: {
            x: xpos,
            y: ypos
        },
    });

    layer.add(hexagon);
}

function addPawn(layer, stage, xpos, ypos, color) {
    var scale = Math.random();
    var stroke = 'black';
    if(color === 'black'){
        stroke = 'white';
    }

    var circle = new Konva.Circle({
        x: xpos,
        y: ypos,
        radius: 16,
        fill: color,
        stroke: stroke,
        strokeWidth: 4,
        draggable: true,
        shadowColor: 'black',
        shadowBlur: 10,
        shadowOffset: {
            x : 5,
            y : 5
        },
        shadowOpacity: 0.6,
        base: {
            x: xpos,
            y: ypos
        },
    });

    layer.add(circle);
}

var stage = new Konva.Stage({
    container: 'container',
    width: width,
    height: height
});

function decodeColor(clr){
    switch(clr){
        case '':
            return 'tan';
            break;
        case 'BLE':
            return 'blue';
            break;
        case 'BCK':
            return 'black';
            break;
        case 'GRE':
            return 'green';
            break;
        case 'WHT':
            return 'white';
            break;
        case 'YEL':
            return 'yellow';
            break;
        case 'RED':
            return 'red';
            break;
    }
}

var layer = new Konva.Layer();
var dragLayer = new Konva.Layer();

var globalJsonVar;

$(document).ready(() => {
    console.log("wsconn.js init");

    let WS = window['MozWebSocket'] ? MozWebSocket : WebSocket

    let data = {
        type: "greet",
        value: "dupnij se lolka czlowieniu"
    };

    let connection = new WS($("#container").data("ws-url"));

    console.log(connection);

    connection.onmessage = function (event) {
        let data = JSON.parse(event.data);
        console.log(data);

        if(data.type === 'map'){
            console.log('succ');
            var index = 0;
            var fields = data.fields;
            for(index = 0; index < fields.length; index++){
                addField(layer, stage, (fields[index].x * 24) + 24, (fields[index].y * 40) + 30, decodeColor(fields[index].type));
            }

            var pawns = data.pawns;
            for(index = 0; index < pawns.length; index++){
                addPawn(layer, stage, (pawns[index].x * 24) + 24, (pawns[index].y * 40) + 30, decodeColor(pawns[index].color));
            }
        }

        stage.add(layer);

    };
    connection.onopen = function (event) {
        connection.send(JSON.stringify(data));

        setInterval(function () {
            // data.value = $("#messagebox").val();
            connection.send(JSON.stringify(data));
        }, 5000);
    };
});