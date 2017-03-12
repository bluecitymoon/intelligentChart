(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanPaymentToolDetailController', PersonFanPaymentToolDetailController);

    PersonFanPaymentToolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFanPaymentTool', 'Person', 'PaymentTool'];

    function PersonFanPaymentToolDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFanPaymentTool, Person, PaymentTool) {
        var vm = this;

        vm.personFanPaymentTool = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFanPaymentToolUpdate', function(event, result) {
            vm.personFanPaymentTool = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
