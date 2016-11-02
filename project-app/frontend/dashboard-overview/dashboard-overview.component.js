angular.
  module('dashboardOverview').
    component('dashboardOverview',{
      templateUrl: 'dashboard-overview/dashboard-overview.template.html',
      controller:
         function DashboardController($scope, $timeout) {

           $scope.counter = 77;
           $scope.temperature= 33;

           $scope.speed= 55;




              var countUp = function() {
                  $scope.counter+= 1;
                  $timeout(countUp, 700);
              }


              var tempUp = function() {
                  var random = Math.random()*10-5;
                  $scope.temperature+= parseInt(random);
                  $timeout(tempUp, 500);
              }

              var speedUp = function() {
                var random = Math.random()*20-10;
                $scope.speed+= parseInt(random);
                $timeout(speedUp, 300);
              }

              $timeout(countUp, 1);
              $timeout(tempUp, 1);
              $timeout(speedUp, 1);



            this.chartdata = [
              {
                text: "erster",
                age: 3
              },
              {
                text: "zweiter",
                age: 1
              },
              {
                text:"dritter",
                age: 2
              }
            ];

            this.chart = null;

            $scope.showGraph = function() {

              setTimeout(function(){

              /*    $scope.chart3 = c3.generate({
                          bindto: '#chart3',
                          data: {
                            columns: [
                              ['material1', 30, 200, 100, 400, 150, 250],
                              ['material2', 50, 20, 10, 40, 15, 25],
                              ['material3', 100, 200, 1000, 900, 500]
                            ]
                          },
                          grid: {
                            x: {
                                lines: [{value: 2}, {value: 4, class: 'grid4', text: 'LABEL 4'}]
                            },
                            y: {
                                lines: [{value: 500}, {value: 800, class: 'grid800', text: 'MAXHEAT'}]
                            }
                        }
                      }); */

                      var chart = c3.generate({
                                bindto: '#chart3',
                                data: {
                                    x: 'x',
                            //        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                                    columns: [
                                        ['x', '2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06'],
                            //            ['x', '20130101', '20130102', '20130103', '20130104', '20130105', '20130106'],
                                        ['material1', 30, 200, 240, 400, 150, 250],
                                        ['material2', 130, 340, 200, 500, 250, 350]
                                    ]
                                },
                                axis: {
                                    x: {
                                        type: 'timeseries',
                                        tick: {
                                            format: '%Y-%m-%d'
                                        }
                                      },
                                    y: {
                                      label: 'production time'
                                    }
                                },
                                grid: {
                                  y: {
                                      lines: [{value: 400}, {value: 800, class: 'grid800', text: 'MAXHEAT'}]
                                  }
                                }
                            });


                    $scope.chart5 = c3.generate({
                            bindto: '#chart4',
                            data: {
                              columns: [
                                ['Ausschuss', 30],
                                ['Erfolgreich', 120],
                              ],
                              type : 'donut'
                            }
                        });

                    }, 3);

                            for (var k = 1; k <= 15; k++)  {
                                if (k % 3 === 0){
                                  setTimeout(function(){

                                    $scope.chart5.unload({
                                       ids: 'Erfolgreich'
                                     });
                                     $scope.chart5.unload({
                                        ids: 'Ausschuss'
                                      });
                                    $scope.chart5.load({
                                      columns: [
                                        ['Ausschuss', 30],
                                        ['Erfolgreich', 120]
                                      ]
                                        });
                                    }, 3000*k);

                                } else {
                                  if ( k % 2 === 0 ){
                                    setTimeout(function(){
                                      $scope.chart5.load({
                                        columns: [
                                          ['Ausschuss', 40],
                                          ['Erfolgreich', 110]
                                        ]
                                            });
                                      }, 3000*k);
                                  } else{
                                    setTimeout(function(){
                                      $scope.chart5.load({
                                        columns: [
                                          ['Ausschuss', 50],
                                          ['Erfolgreich', 100]
                                        ]
                                            });
                                      }, 3000*k);
                                  }
                                }
                            }


          }
        }

    });
