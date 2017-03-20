(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkShoppingDetailController', PersonNetworkShoppingDetailController);

    PersonNetworkShoppingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonNetworkShopping', 'Person', 'NetworkShoppingType'];

    function PersonNetworkShoppingDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonNetworkShopping, Person, NetworkShoppingType) {
        var vm = this;

        vm.personNetworkShopping = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personNetworkShoppingUpdate', function(event, result) {
            vm.personNetworkShopping = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
