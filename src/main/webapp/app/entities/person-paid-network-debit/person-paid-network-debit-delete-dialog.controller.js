(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPaidNetworkDebitDeleteController',PersonPaidNetworkDebitDeleteController);

    PersonPaidNetworkDebitDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonPaidNetworkDebit'];

    function PersonPaidNetworkDebitDeleteController($uibModalInstance, entity, PersonPaidNetworkDebit) {
        var vm = this;

        vm.personPaidNetworkDebit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonPaidNetworkDebit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
