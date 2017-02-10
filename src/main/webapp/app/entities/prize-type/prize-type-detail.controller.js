(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeTypeDetailController', PrizeTypeDetailController);

    PrizeTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrizeType'];

    function PrizeTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, PrizeType) {
        var vm = this;

        vm.prizeType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:prizeTypeUpdate', function(event, result) {
            vm.prizeType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
