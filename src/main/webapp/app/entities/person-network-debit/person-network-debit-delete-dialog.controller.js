(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkDebitDeleteController',PersonNetworkDebitDeleteController);

    PersonNetworkDebitDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonNetworkDebit'];

    function PersonNetworkDebitDeleteController($uibModalInstance, entity, PersonNetworkDebit) {
        var vm = this;

        vm.personNetworkDebit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonNetworkDebit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
