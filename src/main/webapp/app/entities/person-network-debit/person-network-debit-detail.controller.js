(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkDebitDetailController', PersonNetworkDebitDetailController);

    PersonNetworkDebitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonNetworkDebit', 'Person'];

    function PersonNetworkDebitDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonNetworkDebit, Person) {
        var vm = this;

        vm.personNetworkDebit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personNetworkDebitUpdate', function(event, result) {
            vm.personNetworkDebit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
