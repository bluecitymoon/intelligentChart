(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ConnectionLevelDetailController', ConnectionLevelDetailController);

    ConnectionLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ConnectionLevel'];

    function ConnectionLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, ConnectionLevel) {
        var vm = this;

        vm.connectionLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:connectionLevelUpdate', function(event, result) {
            vm.connectionLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
