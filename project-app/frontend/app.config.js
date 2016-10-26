

  angular.module('dashboardApp').
    config(['$routeProvider',
      function ($routeProvider){
        $routeProvider.
          when('/charts', {
            template: '<result-charts></result-charts>'
          }).
          when('/material', {
            template: '<material-list></material-list>'
          }).
          when('/customer', {
            template: '<customer-list></customer-list>'
          }).
          when('/dashboard', {
            template: '<dashboard-overview></dashboard-overview>'
          }).
          otherwise({
            redirectTo: "/dashboard"
          });
    }]);


/*
    angular.module('dashboardApp').
    controller('myCtrl', function($scope) {
      $scope.x1 = "JOHN";
      $scope.x2 = angular.lowercase($scope.x1);
    });
*/
