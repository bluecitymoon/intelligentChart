(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonRegionConnectionDeleteController',PersonRegionConnectionDeleteController);

    PersonRegionConnectionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonRegionConnection'];

    function PersonRegionConnectionDeleteController($uibModalInstance, entity, PersonRegionConnection) {
        var vm = this;

        vm.personRegionConnection = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonRegionConnection.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
