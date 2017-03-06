(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NetworkShoppingTypeDetailController', NetworkShoppingTypeDetailController);

    NetworkShoppingTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NetworkShoppingType'];

    function NetworkShoppingTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, NetworkShoppingType) {
        var vm = this;

        vm.networkShoppingType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:networkShoppingTypeUpdate', function(event, result) {
            vm.networkShoppingType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
