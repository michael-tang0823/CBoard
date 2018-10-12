/**
 * Created by yfyuan on 2016/12/5.
 */
cBoard.controller('loginCtrl', ['$rootScope', '$scope', '$http', '$state', '$filter', function ($rootScope, $scope, $http, $state, $filter) {

    $scope.login = function() {
        $http.post("/login", {username: $scope.username, password: $scope.password}).success(function (response) {

            $state.go('home');

            $rootScope.$emit('login-success');

        });

    }

}]);