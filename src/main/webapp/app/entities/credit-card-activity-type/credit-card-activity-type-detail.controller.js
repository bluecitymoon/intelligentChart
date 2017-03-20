(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('CreditCardActivityTypeDetailController', CreditCardActivityTypeDetailController);

    CreditCardActivityTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CreditCardActivityType'];

    function CreditCardActivityTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CreditCardActivityType) {
        var vm = this;

        vm.creditCardActivityType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:creditCardActivityTypeUpdate', function(event, result) {
            vm.creditCardActivityType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
