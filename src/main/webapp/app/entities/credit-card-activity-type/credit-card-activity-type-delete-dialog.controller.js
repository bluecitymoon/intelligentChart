(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('CreditCardActivityTypeDeleteController',CreditCardActivityTypeDeleteController);

    CreditCardActivityTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CreditCardActivityType'];

    function CreditCardActivityTypeDeleteController($uibModalInstance, entity, CreditCardActivityType) {
        var vm = this;

        vm.creditCardActivityType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CreditCardActivityType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
