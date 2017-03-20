(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansEgeLevelDetailController', PersonFansEgeLevelDetailController);

    PersonFansEgeLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFansEgeLevel', 'Person', 'EgeLevel'];

    function PersonFansEgeLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFansEgeLevel, Person, EgeLevel) {
        var vm = this;

        vm.personFansEgeLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFansEgeLevelUpdate', function(event, result) {
            vm.personFansEgeLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
