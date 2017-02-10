(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PopularityTypeDetailController', PopularityTypeDetailController);

    PopularityTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PopularityType', 'PersonPopularity'];

    function PopularityTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, PopularityType, PersonPopularity) {
        var vm = this;

        vm.popularityType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:popularityTypeUpdate', function(event, result) {
            vm.popularityType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
