(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonRegionConnectionDetailController', PersonRegionConnectionDetailController);

    PersonRegionConnectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonRegionConnection', 'Person', 'Region'];

    function PersonRegionConnectionDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonRegionConnection, Person, Region) {
        var vm = this;

        vm.personRegionConnection = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personRegionConnectionUpdate', function(event, result) {
            vm.personRegionConnection = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
