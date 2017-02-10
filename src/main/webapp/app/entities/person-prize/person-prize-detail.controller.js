(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPrizeDetailController', PersonPrizeDetailController);

    PersonPrizeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonPrize', 'PrizeType', 'PrizeGroup', 'PrizeLevel', 'Person'];

    function PersonPrizeDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonPrize, PrizeType, PrizeGroup, PrizeLevel, Person) {
        var vm = this;

        vm.personPrize = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personPrizeUpdate', function(event, result) {
            vm.personPrize = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
