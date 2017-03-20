(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonTaxiActivityDetailController', PersonTaxiActivityDetailController);

    PersonTaxiActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonTaxiActivity', 'Person'];

    function PersonTaxiActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonTaxiActivity, Person) {
        var vm = this;

        vm.personTaxiActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personTaxiActivityUpdate', function(event, result) {
            vm.personTaxiActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
