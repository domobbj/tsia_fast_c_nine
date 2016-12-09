<style>
    .total {
        width : 3170px;
        height : 2350px;
        background : url(/h5service/css/total.png);
    }
    .A {
        width: 1880px;
        height: 300px;
        float: left;
        margin: 0 200 80 0;
    }
    .B {
        width:926px;
        height: 300px;
        float: left;
        margin: 0 0 80 160;
    }
    .C {
        width: 1500px;
        height: 620px;
        float: left;
        clear: left;
        margin: 70 0 20 0;
    }
    .D {
        width: 2720px;
        height: 570px;
        float: left;
        clear: left;
        margin: 50 0 37;
    }
    .E {
        width: 2720px;
        height: 570px;
        float: left;
        clear: left;
        margin: 0 0;
    }
    .seat_10 {
        width: 96;
        height: 554;
        float: left;
        margin-left: 206;
        margin-top: 40;
        margin-right: 79;
    }
    .seat_8 {
        width: 110;
        height: 500;
        float: left;
        margin-left: 193;
        margin-top: 40;
        margin-right: 77;
    }
    .seat_5 {
        width: 48;
        height: 554;
        float: left;
        margin-left: 204;
        margin-top: 40;
        margin-right: 79;
    }
    .seat_4 {
        width: 110;
        height: 256;
        float: left;
        /* border: solid 1px #CCC; */
        margin-left: 105;
        margin-top: 49;
        margin-right: 96;
    }
    .seat_1 {
        width: 55;
        height: 127;
        float: left;
    }
    .seat_c {
        width: 48;
        height: 111;
        float: left;
    }
    .red {
        background: red
    }
    .green {
        background: green
    }
    span{
        font-size:34px;
        color:beige;
    }
    #B1 {
	   margin-right:91
    }
    #B3 {
	   margin-left:99;
    }
    #C1 {
	   margin-left:186;
    }
</style>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="/h5service/bower_components/angularjs/angular.min.js"></script>
<script src="/h5service/js/controller.js"></script>
</head>


<!-- A区 -->
<div class="total" ng-app="map" ng-controller="mymap">
    <div class="A">
        <div ng-repeat="(key, seat_part) in seats['A']" class = "seat_4" id="A{{key}}">
            <div ng-repeat = "v in seat_part" ng-class="change('seat_1', 'A' + key + $index)" id="A{{key + v}}">
                <span>A{{key+" "+v}}</span>
            </div>
        </div>
    </div>
    
    <div class="B">
        <div ng-repeat="(key, seat_part) in seats['B']" class = "seat_4" id="B{{key}}">
            <div ng-repeat = "v in seat_part" ng-class="change('seat_1', 'B' + key + $index)" id="A{{key + v}}">
                <span>B{{key+" "+v}}</span>
            </div>
        </div>
    </div>
    
    <div class="C">
        <div ng-repeat="(key, seat_part) in seats['C']" class = "seat_{{seat_part.length}}" id="C{{key}}">
            <div ng-repeat = "v in seat_part" ng-class="change('seat_C', 'C' + key + $index)" id="A{{key + v}}">
                <span>C{{key+" "+v}}</span>
            </div>
        </div>
    </div>
    
    <div class="D">
        <div ng-repeat="(key, seat_part) in seats['D']" class = "seat_{{seat_part.length}}" id="C{{key}}">
            <div ng-repeat = "v in seat_part" ng-class="change('seat_1','D' + key + $index)" id="A{{key + v}}">
                <span>D{{key+" "+v}}</span>
            </div>
        </div>
    </div>
    
    <div class="E">
        <div ng-repeat="(key, seat_part) in seats['E']" class = "seat_{{seat_part.length}}" id="C{{key}}">
            <div ng-repeat = "v in seat_part" ng-class="change('seat_1', 'E' + key + $index)" id="A{{key + v}}">
                <span>E{{key+" "+v}}</span>
            </div>
        </div>
    </div>
</div>

<script>
var g_seats = <?php echo json_encode($model->seat)?>;
var myself = <?php echo json_encode($my)?>;
var seat = <?php echo json_encode($seat)?>;
</script>
