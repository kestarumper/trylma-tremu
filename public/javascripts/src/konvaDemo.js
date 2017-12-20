var width = window.innerWidth-200;
var height = window.innerHeight-200;

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
        dragBoundFunc: function(pos) {
            var dist = Math.sqrt(
                (this.attrs.base.x - pos.x)*(this.attrs.base.x - pos.x) +
                (this.attrs.base.y - pos.y)*(this.attrs.base.y - pos.y)
            );

            var scale = 100 / dist;

            if(scale < 1)
                return {
                    x: Math.round((pos.x - this.attrs.base.x) * scale + this.attrs.base.x),
                    y: Math.round((pos.y - this.attrs.base.y) * scale + this.attrs.base.y)
                };
            else
                return pos;
        }
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

for(var n = 0; n < 10; n++) {
    for(var h = 0; h < 10; h++) {
        addStar(layer, stage, n*50*2+50, h*50*2+50);
    }
}

stage.add(layer, dragLayer);

stage.on('dragstart', function(evt) {
    var shape = evt.target;

    shape.setFill("blue");
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
    shape.setFill("red");
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
    shape.attrs.base.x = shape.getAbsolutePosition().x;
    shape.attrs.base.y = shape.getAbsolutePosition().y;
});