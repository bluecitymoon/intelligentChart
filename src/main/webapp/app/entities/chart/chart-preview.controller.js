(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ChartPreviewController', ChartPreviewController);

    ChartPreviewController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'Chart'];

    function ChartPreviewController($scope, $rootScope, $stateParams, previousState, Chart) {
        var vm = this;

        $scope.config = {
            title: "柱图",
            subtitle: 'Bar Chart Subtitle',
            height: 500
        };

        Chart.get({id : $stateParams.id}).$promise.then(function (entity) {
            vm.chart = entity;

            $scope.config.title = entity.identifier;

            loadChartData();
        });

        function loadChartData() {

            Chart.queryData({id : $stateParams.id}).$promise.then(function (numbers) {

                var titles = numbers.titles;
                var dataSet = numbers.numbers;

                var pageload = {
                    name: "柱图",
                    datapoints: []
                };

                angular.forEach(titles, function (title, index) {

                    var number = dataSet[index];
                    pageload.datapoints.push({ x: title, y: number});

                });

                $scope.data = [ pageload ];

            });
        }
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:chartUpdate', function(event, result) {
            vm.chart = result;
        });

        $scope.$on('$destroy', unsubscribe);
    }
})();
