(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeGroupDetailController', PrizeGroupDetailController);

    PrizeGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrizeGroup'];

    function PrizeGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, PrizeGroup) {
        var vm = this;

        vm.prizeGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:prizeGroupUpdate', function(event, result) {
            vm.prizeGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
