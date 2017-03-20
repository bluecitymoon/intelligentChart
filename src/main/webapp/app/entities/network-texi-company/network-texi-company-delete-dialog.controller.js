(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NetworkTexiCompanyDeleteController',NetworkTexiCompanyDeleteController);

    NetworkTexiCompanyDeleteController.$inject = ['$uibModalInstance', 'entity', 'NetworkTexiCompany'];

    function NetworkTexiCompanyDeleteController($uibModalInstance, entity, NetworkTexiCompany) {
        var vm = this;

        vm.networkTexiCompany = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NetworkTexiCompany.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
