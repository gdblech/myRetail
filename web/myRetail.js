var app = angular.module("MyRetail Products", []);


app.controller('main', [
    '$scope', '$http',
    function ($scope,$http) {

        $scope.getProduct =function () {
            if(!$scope.pid || $scope.pid === ''){
                return
            }

            $http.get('http://localhost:8080/product/'+ $scope.pid).then(function (response) {
                $scope.response = response.data;
            });
        };

        $scope.update = function () {
            if(!$scope.pid || $scope.pid === ''){
                return
            }

            if(!$scope.editName || !$scope.editValue || !$scope.editCurrency ||
                $scope.editName === '' || $scope.editValue === '' || $scope.editCurrency === ''){
                    return;
            }

            var updated = {"name":$scope.editName, "pid":$scope.pid, "currentPrice":{"currencyCode":$scope.editCurrency, "value": $scope.editValue}};

            $http.put('http://localhost:8080/product/'+ $scope.pid, updated).then(function (response) {
                $scope.response = response.data;
            });
        };

        $scope.newProduct = function () {
            if(!$scope.newName || !$scope.newValue || !$scope.newCurrency || !$scope.ID ||
                $scope.newName === '' || $scope.newValue === '' || $scope.newCurrency === '' || $scope.newID === ''){
                return;
            }
            var newProd = {"name":$scope.newName, "pid":$scope.newID, "currentPrice":{"currencyCode":$scope.newCurrency, "value": $scope.newValue}};

            $http.put('http://localhost:8080/product/', newProd).then(function (response) {
                $scope.response = response.data;
            });
        };
    }
]);