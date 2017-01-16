(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ChartDetailController', ChartDetailController);

    ChartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Chart'];

    function ChartDetailController($scope, $rootScope, $stateParams, previousState, entity, Chart) {
        var vm = this;

        vm.chart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:chartUpdate', function(event, result) {
            vm.chart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
