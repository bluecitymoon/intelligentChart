(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonTaxiActivityDeleteController',PersonTaxiActivityDeleteController);

    PersonTaxiActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonTaxiActivity'];

    function PersonTaxiActivityDeleteController($uibModalInstance, entity, PersonTaxiActivity) {
        var vm = this;

        vm.personTaxiActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonTaxiActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
