var width = window.innerWidth-200;
var height = window.innerHeight-200;
var howmanyhexagons = parseInt(document.getElementById(howmanyhexagons).value);

var tween = null;

function addStar(layer, stage, xpos, ypos) {
    var scale = Math.random();

    var hexagon = new Konva.RegularPolygon({
        x: xpos,
        y: ypos,
        sides: 6,
        radius: 50,
        fill: 'red',
        stroke: 'black',
        strokeWidth: 4,
        draggable: true,
        // scale: {
        //     x : scale,
        //     y : scale
        // },
        // rotation: Math.random() * 180,
        shadowColor: 'black',
        shadowBlur: 10,
        shadowOffset: {
            x : 5,
            y : 5
        },
        shadowOpacity: 0.6,
        // custom attribute
        // startScale: scale
    });

    layer.add(hexagon);
}
var stage = new Konva.Stage({
    container: 'container',
    width: width,
    height: height
});

var layer = new Konva.Layer();
var dragLayer = new Konva.Layer();

for(var n = 0; n < howmanyhexagons; n++) {
    addStar(layer, stage, n*50*2+50, n);
}

stage.add(layer, dragLayer);

stage.on('dragstart', function(evt) {
    var shape = evt.target;
    // moving to another layer will improve dragging performance
    shape.moveTo(dragLayer);
    stage.draw();

    if (tween) {
        tween.pause();
    }
    shape.setAttrs({
        shadowOffset: {
            x: 15,
            y: 15
        },
        // scale: {
        //     x: shape.getAttr('startScale') * 1.2,
        //     y: shape.getAttr('startScale') * 1.2
        // }
    });
});

stage.on('dragend', function(evt) {
    var shape = evt.target;
    shape.moveTo(layer);
    stage.draw();
    shape.to({
        duration: 0.5,
        easing: Konva.Easings.ElasticEaseOut,
        // scaleX: shape.getAttr('startScale'),
        // scaleY: shape.getAttr('startScale'),
        shadowOffsetX: 5,
        shadowOffsetY: 5
    });
});