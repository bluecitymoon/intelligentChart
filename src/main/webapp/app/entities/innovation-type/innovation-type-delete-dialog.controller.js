(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('InnovationTypeDeleteController',InnovationTypeDeleteController);

    InnovationTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'InnovationType'];

    function InnovationTypeDeleteController($uibModalInstance, entity, InnovationType) {
        var vm = this;

        vm.innovationType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InnovationType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
