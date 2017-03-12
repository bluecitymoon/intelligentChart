(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanPaymentToolDeleteController',PersonFanPaymentToolDeleteController);

    PersonFanPaymentToolDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFanPaymentTool'];

    function PersonFanPaymentToolDeleteController($uibModalInstance, entity, PersonFanPaymentTool) {
        var vm = this;

        vm.personFanPaymentTool = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFanPaymentTool.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
