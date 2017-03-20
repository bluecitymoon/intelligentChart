(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonIncomeDeleteController',PersonIncomeDeleteController);

    PersonIncomeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonIncome'];

    function PersonIncomeDeleteController($uibModalInstance, entity, PersonIncome) {
        var vm = this;

        vm.personIncome = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonIncome.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
