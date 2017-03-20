(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonCreditCardActivityDetailController', PersonCreditCardActivityDetailController);

    PersonCreditCardActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonCreditCardActivity', 'Person', 'CreditCardActivityType'];

    function PersonCreditCardActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonCreditCardActivity, Person, CreditCardActivityType) {
        var vm = this;

        vm.personCreditCardActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personCreditCardActivityUpdate', function(event, result) {
            vm.personCreditCardActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
