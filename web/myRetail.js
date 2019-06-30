var app = angular.module("MyRetail Products", []);


app.controller('main', [
    '$scope', '$http',
    function ($scope,$http) {

        $scope.getProduct =function () {
            $http.get('http://localhost:8080/product/'+ $scope.pid).then(function (response) {
                $scope.response = response.data;
            });
        };
    }
]);

app.controller('editMain', [
   '$scope', '$http',
   function ($scope, $http) {
       $scope.update = function () {
            //todo push to backend
       };
   }
]);

app.controller('newMain', [
    '$scope', '$http',
    function ($scope, $http) {
        $scope.newProduct = function () {

        };
        $scope.checkPID = function () {
            //todo get request to check if id is valid
        };
    }
]);

