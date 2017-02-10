(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonConnectionLevelDetailController', PersonConnectionLevelDetailController);

    PersonConnectionLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonConnectionLevel', 'Person', 'ConnectionLevel'];

    function PersonConnectionLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonConnectionLevel, Person, ConnectionLevel) {
        var vm = this;

        vm.personConnectionLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personConnectionLevelUpdate', function(event, result) {
            vm.personConnectionLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
