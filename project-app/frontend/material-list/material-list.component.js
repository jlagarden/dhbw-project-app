angular.
  module('materialList').
    component('materialList',{
      templateUrl: 'material-list/material-list.template.html',
      controller: function MaterialListController($scope) {
        $scope.materialdata = [
          {
              number: 4728,
              amount: 4,
              rejects: 1,
              prodtime: 22.4
          },
          {
              number: 6346,
              amount: 7,
              rejects: 5,
              prodtime: 17.4
          },
          {
              number: 4728,
              amount: 4,
              rejects: 1,
              prodtime: 22.4
          },
          {
              number: 4728,
              amount: 4,
              rejects: 1,
              prodtime: 22.4
          },
          {
              number: 4728,
              amount: 4,
              rejects: 1,
              prodtime: 22.4
          },
          {
              number: 4728,
              amount: 4,
              rejects: 1,
              prodtime: 22.4
          },
          {
              number: 4728,
              amount: 4,
              rejects: 1,
              prodtime: 22.4
          },
        ]
      }

    });
