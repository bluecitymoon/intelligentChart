(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotLogDetailController', RobotLogDetailController);

    RobotLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RobotLog', 'Robot'];

    function RobotLogDetailController($scope, $rootScope, $stateParams, previousState, entity, RobotLog, Robot) {
        var vm = this;

        vm.robotLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:robotLogUpdate', function(event, result) {
            vm.robotLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
