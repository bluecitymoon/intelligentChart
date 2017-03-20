(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSearchCountDetailController', PersonSearchCountDetailController);

    PersonSearchCountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonSearchCount', 'Person'];

    function PersonSearchCountDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonSearchCount, Person) {
        var vm = this;

        vm.personSearchCount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personSearchCountUpdate', function(event, result) {
            vm.personSearchCount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
