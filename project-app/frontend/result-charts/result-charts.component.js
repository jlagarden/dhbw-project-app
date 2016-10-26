angular.
  module('resultCharts').
    component('resultCharts',{
      templateUrl: 'result-charts/result-charts.template.html',
      controller:
         function C3ChartController($scope) {

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
                $scope.chart = c3.generate({
                        bindto: '#chart3',
                        data: {
                          columns: [
                            ['data1', 30, 200, 100, 400, 150, 250],
                            ['data2', 50, 20, 10, 40, 15, 25]
                          ],
                          type:"bar"
                        }
                    });
                $scope.chart2 = c3.generate({
                        bindto: '#chart2',
                        data: {
                          columns: [
                            ['data', 81.4]
                          ],
                          type: 'gauge'
                        }
                    });
              $scope.chart3 = c3.generate({
                      bindto: '#chart4',
                      data: {
                        columns: [
                          ['data1', 30, 200, 100, 400, 150, 250],
                          ['data2', 50, 20, 10, 40, 15, 25],
                          ['data3', 100, 200, 1000, 900, 500]
                        ]
                      },
                      grid: {
                        x: {
                            lines: [{value: 2}, {value: 4, class: 'grid4', text: 'LABEL 4'}]
                        },
                        y: {
                            lines: [{value: 500}, {value: 800, class: 'grid800', text: 'LABEL 800'}]
                        }
                    }
                  });
            $scope.chart4 = c3.generate({
                    bindto: '#chart',
                    data: {
                      columns: [
                          ['data1', 300, 350, 300, 0, 0, 0],
                          ['data2', 130, 100, 140, 200, 150, 50]
                      ],
                      types: {
                          data1: 'area',
                          data2: 'area-spline'
                      }
                    }
                });
                $scope.chart5 = c3.generate({
                        bindto: '#chart5',
                        data: {
                          columns: [
                            ['Erfolgreich', 30],
                            ['Ausschuss', 120],
                          ],
                          type : 'donut'
                        }
                    });
    /*            $scope.chart5 = c3.generate({
                        bindto: '#chart6',
                        data: {
                          columns:[
                              ['data1', 30, 20, 50, 40, 60, 50],
                              ['data2', 200, 130, 90, 240, 130, 220],
                              ['data3', 300, 200, 160, 400, 250, 250],
                              ['data4', 200, 130, 90, 240, 130, 220],
                              ['data5', 130, 120, 150, 140, 160, 150],
                              ['data6', 90, 70, 20, 50, 60, 120],
                          ],
                          type: 'bar',
                          types: {
                              data3: 'spline',
                              data4: 'line',
                              data6: 'area',
                          },
                          groups: [
                              ['data1','data2']
                          ]
                        }
                    }); */
}, 1);


                for (var i = 1; i <= 15; i++)  {
                    if (i % 2 === 0){
                      setTimeout(function(){
                        $scope.chart.load({
                                columns: [
                                    ['data1', 230, 190, 300, 500, 300, 400],
                                    ['data2', 20, 50, 2, 20, 30, 55]
                                ]
                            });
                        }, 3000*i);
                    } else {
                      setTimeout(function(){
                        $scope.chart.load({
                          columns: [
                              ['data1', 30, 200, 100, 400, 150, 250],
                              ['data2', 50, 20, 10, 40, 15, 25]
                                    ]
                                });
                          }, 3000*i);
                  }
                }


                for (var j = 1; j <= 15; j++)  {
                    if (j % 3 === 0){
                      setTimeout(function(){
                        $scope.chart2.load({
                                columns: [
                                  ['data', 25.8]
                                ]
                            });
                        }, 3000*j);
                    } else {
                      if ( j % 2 === 0 ){
                        setTimeout(function(){
                          $scope.chart2.load({
                                  columns: [
                                    ['data', 50.6]
                                  ]
                                });
                          }, 3000*j);
                      } else{
                        setTimeout(function(){
                          $scope.chart2.load({
                                  columns: [
                                    ['data', 84.6]
                                  ]
                                });
                          }, 3000*j);
                      }
                    }
                }


        //        for (var k = 1; k <= 15; k++)  {
          //          if (k % 3 === 0){
          /*            setTimeout(function(){
                        $scope.chart5.unload({
                           ids: 'Erfolgreich'
                         });
                         $scope.chart5.unload({
                            ids: 'Ausschuss'
                          });
                        $scope.chart5.load({
                          columns: [
                            ['Erfolgreich', 20],
                            ['Ausschuss', 130]
                          ]
                            });
                        }, 3000*k);
                        */
            /*        } else {
                      if ( k % 2 === 0 ){
                        setTimeout(function(){
                          $scope.chart5.load({
                            columns: [
                              ['Erfolgreich', 40],
                              ['Ausschuss', 110]
                            ]
                                });
                          }, 3000*k);
                      } else{
                        setTimeout(function(){
                          $scope.chart5.load({
                            columns: [
                              ['Erfolgreich', 50],
                              ['Ausschuss', 120]
                            ]
                                });
                          }, 3000*k);
                      } */
                    //}
                //}

            };


            $scope.updateGraph = function() {
              $scope.chart.load({
                      columns: [
                          ['data1', 230, 190, 300, 500, 300, 400]
                      ]
                  });
            }
          }

    });
