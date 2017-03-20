(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPaidNetworkDebitDetailController', PersonPaidNetworkDebitDetailController);

    PersonPaidNetworkDebitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonPaidNetworkDebit', 'Person'];

    function PersonPaidNetworkDebitDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonPaidNetworkDebit, Person) {
        var vm = this;

        vm.personPaidNetworkDebit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personPaidNetworkDebitUpdate', function(event, result) {
            vm.personPaidNetworkDebit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
