var width = 900;
var height = 1000;
const OFFSETX = 45;
const OFFSETY = 45;
const XWIDTH = 32;
const YWIDTH = 55;

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
        radius: 34,
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

function addPawn(layer, stage, xpos, ypos, color, movable) {
    var scale = Math.random();
    var stroke = 'black';
    if(color === 'black'){
        stroke = 'white';
    }

    var circle = new Konva.Circle({
        x: xpos,
        y: ypos,
        radius: 24,
        fill: color,
        stroke: stroke,
        strokeWidth: 4,
        draggable: movable,
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

var fieldsLayer = new Konva.Layer();
var pawnsLayer = new Konva.Layer();
var tempLayer = new Konva.Layer();
var initialized = false;
var isMoving = false;

var moves = {
    'type' : "move",
    'x1' : -1,
    'y1' : -1,
    'x2' : -1,
    'y2' : -1,
    username: $("#username").val()
};


$(document).ready(() => {
    console.log("wsconn.js init");

    let WS = window['MozWebSocket'] ? MozWebSocket : WebSocket

    let connection = new WS($("#container").data("ws-url"));

    $("#scores").hide();
    console.log(connection);

    connection.onopen = function (event) {
        let data = {
            type: "WebSocketInit",
            username: $("#username").val()
        };

        connection.send(JSON.stringify(data));
    };

    connection.onmessage = function (event) {
        let data = JSON.parse(event.data);
        console.log(data);

        if(data.type === 'redirect') {
            window.location.replace(data.url);
        }

        if(data.type === 'map' && !initialized){
            stage.destroyChildren()
            console.log('succ');

            var fields = data.fields;
            for(index = 0; index < fields.length; index++){
                addField(fieldsLayer, stage, (fields[index].x * XWIDTH) + OFFSETX, (fields[index].y * YWIDTH) + OFFSETY, decodeColor(fields[index].type));
            }

            var pawns = data.pawns;
            for(index = 0; index < pawns.length; index++){
                addPawn(pawnsLayer, stage, (pawns[index].x * XWIDTH) + OFFSETX, (pawns[index].y * YWIDTH) + OFFSETY, decodeColor(pawns[index].color));
            }
            stage.add(fieldsLayer);
            stage.add(pawnsLayer);
            stage.add(tempLayer);
            initialized = true;
            connection.send(JSON.stringify({'type' : "status", username: $("#username").val()}));

        };

        if(data.type === 'map' && initialized){
            console.log("repainting");
            fieldsLayer.remove();
            stage.destroyChildren();

            var pawns = data.pawns;
            for(index = 0; index < pawns.length; index++){
                addPawn(pawnsLayer, stage, (pawns[index].x * XWIDTH) + OFFSETX, (pawns[index].y * YWIDTH) + OFFSETY, decodeColor(pawns[index].color), isMoving);
            }
            stage.add(fieldsLayer);
            stage.add(pawnsLayer);
            stage.add(tempLayer);
        }

        if(data.type === 'move'){
            if(!data.cond){
                console.log("jamnik");
                connection.send(JSON.stringify({'type' : "repaint", username: $("#username").val()}));
            }
        }

        if(data.type === 'refresh'){
            connection.send(JSON.stringify({'type' : "status", username: $("#username").val()}));
        }

        if(data.type === 'status'){
            isMoving = data.canMove;
            connection.send(JSON.stringify({'type' : "repaint", username: $("#username").val()}));
        }

        if(data.type === 'finish'){
            stage.destroy();
            $("#status_bar").hide();
            $("#scores").show();
            $("#scores ul").empty();
            for(i = 0; i < data.users.length; i++){
                $("#scores ul").append('<li class="list-group-item">' + data.users[i]
                    + '<span class="badge">' + (i + 1) + '</span></li>');
            }
        }

        if(data.type === 'color'){
            $('#player-color').css('background-color', decodeColor(data.color));
        }

        if(isMoving){
            $("#status_text").text("Youre move!");
            $("#passButton").prop('disabled', false);
        }

    };

    connection.onclose = function (event) {
        location.reload(true);
    };

        $('#exit-button').click(function(){
           connection.send(JSON.stringify({'type' : 'exit', 'username' : $("#username").val()}));
            window.location.replace("/");
        });

        $("#passButton").click(function(){
            $("#passButton").prop('disabled', true);
            $("#status_text").text("You'r waiting!");
            isMoving = false;
            connection.send(JSON.stringify({'type' : "pass", username: $("#username").val()}));
        });

        var cordsPawn = { "x" : 0, "y" : 0};
        stage.on("dragstart", function(e){
            e.target.moveTo(tempLayer);
            cordsPawn.x = e.target.x();
            cordsPawn.y = e.target.y();
            pawnsLayer.draw();
        });


        var previousShape;
        var previousColor;
        var cordsField = { "x" : 0, "y" : 0};
        stage.on("dragmove", function(evt){
            var pos = stage.getPointerPosition();
            var shape = fieldsLayer.getIntersection(pos);
            if (previousShape && shape) {
                if (previousShape !== shape) {
                    // leave from old targer
                    previousShape.fire('dragleave', {
                        type : 'dragleave',
                        target : previousShape,
                        evt : evt.evt
                    }, true);

                    // enter new targer
                    previousColor = shape.fill();
                    cordsField.x = shape.x();
                    cordsField.y = shape.y();
                    shape.fire('dragenter', {
                        type : 'dragenter',
                        target : shape,
                        evt : evt.evt
                    }, true);
                    previousShape = shape;
                } else {
                    previousShape.fire('dragover', {
                        type : 'dragover',
                        target : previousShape,
                        evt : evt.evt
                    }, true);
                }
            } else if (!previousShape && shape) {
                previousShape = shape;
                previousColor = shape.fill();
                cordsField.x = shape.x();
                cordsField.y = shape.y();
                shape.fire('dragenter', {
                    type : 'dragenter',
                    target : shape,
                    evt : evt.evt
                }, true);
            } else if (previousShape && !shape) {
                previousShape.fire('dragleave', {
                    type : 'dragleave',
                    target : previousShape,
                    evt : evt.evt
                }, true);
                previousShape = undefined;
            }
        });

        stage.on("dragend", function(e){
            var pos = stage.getPointerPosition();
            var shape = fieldsLayer.getIntersection(pos);
            if (shape) {
                previousShape.fire('drop', {
                    type : 'drop',
                    target : previousShape,
                    evt : e.evt
                }, true);
            }
            previousShape = undefined;
            console.log(moves);
            e.target.moveTo(pawnsLayer);
            e.target.move({
                x: cordsField.x - e.target.x(),
                y: cordsField.y - e.target.y()
            });

            moves.x2 = Math.round((e.target.x() - OFFSETX) / XWIDTH);
            moves.y2 = Math.round((e.target.y() - OFFSETY) / YWIDTH);
            moves.x1 = Math.round((cordsPawn.x - OFFSETX) / XWIDTH);
            moves.y1 = Math.round((cordsPawn.y - OFFSETY) / YWIDTH);

            connection.send(JSON.stringify(moves));

            pawnsLayer.draw();
            fieldsLayer.draw();
            tempLayer.draw();
        });

        stage.on("dragenter", function(e){
            e.target.fill('red');
            fieldsLayer.draw();
        });

        stage.on("dragleave", function(e){
            e.target.fill(previousColor);
            fieldsLayer.draw();
        });

        stage.on("dragover", function(e){
            fieldsLayer.draw();
        });

        stage.on("drop", function(e){
            e.target.fill(previousColor);
            fieldsLayer.draw();
        });

        stage.add(fieldsLayer);
        stage.add(pawnsLayer);
        stage.add(tempLayer);

        // setInterval(function () {
        //     // data.value = $("#messagebox").val();
        //     connection.send(JSON.stringify(data));
        // }, 5000);

});