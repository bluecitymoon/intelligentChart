(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EgeLevelDetailController', EgeLevelDetailController);

    EgeLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EgeLevel'];

    function EgeLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, EgeLevel) {
        var vm = this;

        vm.egeLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:egeLevelUpdate', function(event, result) {
            vm.egeLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
