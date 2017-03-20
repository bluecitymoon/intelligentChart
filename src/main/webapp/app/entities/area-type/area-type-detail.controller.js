(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('AreaTypeDetailController', AreaTypeDetailController);

    AreaTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AreaType'];

    function AreaTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, AreaType) {
        var vm = this;

        vm.areaType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:areaTypeUpdate', function(event, result) {
            vm.areaType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
