angular.
  module('customerList').
    component('customerList',{
      templateUrl: 'customer-list/customer-list.template.html',
      controller: function CustomerListController($scope) {
        $scope.customerdata = [
          {
              number: 4717,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.3
          },
          {
              number: 6345,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.63
          },
          {
              number: 4717,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.3
          },
          {
              number: 4717,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.3
          },
          {
              number: 4717,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.3
          },
          {
              number: 4717,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.3
          },
          {
              number: 4717,
              ordernumber: 'c5bd9a89-d06e-402c-8a84-67df3bb94c57',
              materialnumber: 1757,
              avgrejects: 0.3
          }
        ]
      }

    });
