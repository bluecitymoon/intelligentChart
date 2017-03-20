(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('LawBusinessTypeDetailController', LawBusinessTypeDetailController);

    LawBusinessTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LawBusinessType'];

    function LawBusinessTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, LawBusinessType) {
        var vm = this;

        vm.lawBusinessType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:lawBusinessTypeUpdate', function(event, result) {
            vm.lawBusinessType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
