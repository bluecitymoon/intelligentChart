(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanDisbributionDetailController', PersonFanDisbributionDetailController);

    PersonFanDisbributionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFanDisbribution', 'Person', 'Region'];

    function PersonFanDisbributionDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFanDisbribution, Person, Region) {
        var vm = this;

        vm.personFanDisbribution = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFanDisbributionUpdate', function(event, result) {
            vm.personFanDisbribution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
