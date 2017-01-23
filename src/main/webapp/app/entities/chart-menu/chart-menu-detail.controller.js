(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ChartMenuDetailController', ChartMenuDetailController);

    ChartMenuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartMenu', 'Chart', 'Menu'];

    function ChartMenuDetailController($scope, $rootScope, $stateParams, previousState, entity, ChartMenu, Chart, Menu) {
        var vm = this;

        vm.chartMenu = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:chartMenuUpdate', function(event, result) {
            vm.chartMenu = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
