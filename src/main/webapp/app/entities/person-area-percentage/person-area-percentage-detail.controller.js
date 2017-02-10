(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonAreaPercentageDetailController', PersonAreaPercentageDetailController);

    PersonAreaPercentageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonAreaPercentage', 'Person', 'MediaType', 'AreaType'];

    function PersonAreaPercentageDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonAreaPercentage, Person, MediaType, AreaType) {
        var vm = this;

        vm.personAreaPercentage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personAreaPercentageUpdate', function(event, result) {
            vm.personAreaPercentage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
