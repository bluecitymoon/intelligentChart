(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanSupportGroupDetailController', PersonFanSupportGroupDetailController);

    PersonFanSupportGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFanSupportGroup', 'Person'];

    function PersonFanSupportGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFanSupportGroup, Person) {
        var vm = this;

        vm.personFanSupportGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFanSupportGroupUpdate', function(event, result) {
            vm.personFanSupportGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
