(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotDetailController', RobotDetailController);

    RobotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Robot'];

    function RobotDetailController($scope, $rootScope, $stateParams, previousState, entity, Robot) {
        var vm = this;

        vm.robot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:robotUpdate', function(event, result) {
            vm.robot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
