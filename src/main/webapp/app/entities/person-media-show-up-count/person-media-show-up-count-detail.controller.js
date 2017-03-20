(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonMediaShowUpCountDetailController', PersonMediaShowUpCountDetailController);

    PersonMediaShowUpCountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonMediaShowUpCount', 'Person'];

    function PersonMediaShowUpCountDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonMediaShowUpCount, Person) {
        var vm = this;

        vm.personMediaShowUpCount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personMediaShowUpCountUpdate', function(event, result) {
            vm.personMediaShowUpCount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
