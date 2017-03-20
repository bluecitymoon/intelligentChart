(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PaymentToolDetailController', PaymentToolDetailController);

    PaymentToolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PaymentTool'];

    function PaymentToolDetailController($scope, $rootScope, $stateParams, previousState, entity, PaymentTool) {
        var vm = this;

        vm.paymentTool = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:paymentToolUpdate', function(event, result) {
            vm.paymentTool = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
