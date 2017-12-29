var width = window.innerWidth-200;
var height = window.innerHeight-200;
const OFFSETX = 24;
const OFFSETY = 30;
const XWIDTH = 24;
const YWIDTH = 40;

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

var fieldsLayer = new Konva.Layer();
var pawnsLayer = new Konva.Layer();
var tempLayer = new Konva.Layer();

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
                addField(fieldsLayer, stage, (fields[index].x * XWIDTH) + OFFSETX, (fields[index].y * YWIDTH) + OFFSETY, decodeColor(fields[index].type));
            }

            var pawns = data.pawns;
            for(index = 0; index < pawns.length; index++){
                addPawn(pawnsLayer, stage, (pawns[index].x * XWIDTH) + OFFSETX, (pawns[index].y * YWIDTH) + OFFSETY, decodeColor(pawns[index].color));
            }
        }

        stage.add(pawnsLayer);

    };

    connection.onopen = function (event) {
        connection.send(JSON.stringify(data));

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

            var moves = {
                'x1' : -1,
                'y1' : -1,
                'x2' : -1,
                'y2' : -1
            };

            moves.x2 = Math.round((e.target.x() - OFFSETX) / XWIDTH);
            moves.y2 = Math.round((e.target.y() - OFFSETY) / YWIDTH);
            moves.x1 = Math.round((cordsPawn.x - OFFSETX) / XWIDTH);
            moves.y1 = Math.round((cordsPawn.y - OFFSETY) / YWIDTH);

            console.log(moves);

            connection.send(JSON.stringify(moves));

            e.target.moveTo(pawnsLayer);
            e.target.move({
                x: cordsField.x - e.target.x(),
                y: cordsField.y - e.target.y()
            });
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
    };
});