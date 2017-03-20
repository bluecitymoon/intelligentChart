(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonIncomeDetailController', PersonIncomeDetailController);

    PersonIncomeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonIncome', 'Person'];

    function PersonIncomeDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonIncome, Person) {
        var vm = this;

        vm.personIncome = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personIncomeUpdate', function(event, result) {
            vm.personIncome = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
