(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeLevelDetailController', PrizeLevelDetailController);

    PrizeLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrizeLevel'];

    function PrizeLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, PrizeLevel) {
        var vm = this;

        vm.prizeLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:prizeLevelUpdate', function(event, result) {
            vm.prizeLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
