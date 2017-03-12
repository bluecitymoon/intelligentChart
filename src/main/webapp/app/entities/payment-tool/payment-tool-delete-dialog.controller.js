(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PaymentToolDeleteController',PaymentToolDeleteController);

    PaymentToolDeleteController.$inject = ['$uibModalInstance', 'entity', 'PaymentTool'];

    function PaymentToolDeleteController($uibModalInstance, entity, PaymentTool) {
        var vm = this;

        vm.paymentTool = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PaymentTool.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
