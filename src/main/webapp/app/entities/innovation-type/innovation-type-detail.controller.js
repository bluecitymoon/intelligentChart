(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('InnovationTypeDetailController', InnovationTypeDetailController);

    InnovationTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InnovationType'];

    function InnovationTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, InnovationType) {
        var vm = this;

        vm.innovationType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:innovationTypeUpdate', function(event, result) {
            vm.innovationType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
