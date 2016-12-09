var app = angular.module('map',[]);

app.controller('mymap', function($scope, $http) {
	$scope.seats = g_seats;
	$scope.my = myself;
	$scope.seat = seat;
	$scope.change = function (classname, index) {
		if ($scope.my == index) {
			console.log(index);
			return classname + ' green';
		}
		if ($scope.seat == index) {
			return classname + ' red';
		}
		return classname;
	}
});