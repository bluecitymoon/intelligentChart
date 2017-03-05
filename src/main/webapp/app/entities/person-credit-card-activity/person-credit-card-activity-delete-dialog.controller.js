(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonCreditCardActivityDeleteController',PersonCreditCardActivityDeleteController);

    PersonCreditCardActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonCreditCardActivity'];

    function PersonCreditCardActivityDeleteController($uibModalInstance, entity, PersonCreditCardActivity) {
        var vm = this;

        vm.personCreditCardActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonCreditCardActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
