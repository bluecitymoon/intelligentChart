(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPopularityDetailController', PersonPopularityDetailController);

    PersonPopularityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonPopularity', 'Person', 'PopularityType'];

    function PersonPopularityDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonPopularity, Person, PopularityType) {
        var vm = this;

        vm.personPopularity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personPopularityUpdate', function(event, result) {
            vm.personPopularity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
